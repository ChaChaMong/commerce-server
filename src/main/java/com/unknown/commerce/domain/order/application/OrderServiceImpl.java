package com.unknown.commerce.domain.order.application;

import com.unknown.commerce.domain.item.dao.ItemRepository;
import com.unknown.commerce.domain.item.entity.Item;
import com.unknown.commerce.domain.item.entity.ItemProduct;
import com.unknown.commerce.domain.order.dao.OrderItemRepository;
import com.unknown.commerce.domain.order.dao.OrderProductRepository;
import com.unknown.commerce.domain.order.dao.OrderRepository;
import com.unknown.commerce.domain.order.dto.ItemRequest;
import com.unknown.commerce.domain.order.dto.OrderRequest;
import com.unknown.commerce.domain.order.dto.OrderResponse;
import com.unknown.commerce.domain.order.dto.ProductRequest;
import com.unknown.commerce.domain.order.entity.Order;
import com.unknown.commerce.domain.order.entity.OrderItem;
import com.unknown.commerce.domain.order.entity.OrderProduct;
import com.unknown.commerce.domain.product.dao.ProductRepository;
import com.unknown.commerce.domain.product.entity.Product;
import com.unknown.commerce.global.exception.BusinessException;
import com.unknown.commerce.global.response.HttpResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        for (ItemRequest itemRequest : orderRequest.getOrderItems()) {
            // Step 1: 요청 받은 Item이 실제 DB에 존재하고 정보가 맞는지 확인
            Item item = itemRepository.findByItemId(itemRequest.getItemId())
                    .orElseThrow(() -> new BusinessException(HttpResponse.Fail.NOT_FOUND_ITEM));

            if (!item.getName().equals(itemRequest.getName()) || item.getPrice().compareTo(itemRequest.getPrice()) != 0) {
                throw new BusinessException(HttpResponse.Fail.ITEM_MISMATCH);
            }

            // Step 2: 요청 받은 Item의 Product 구조가 실제 DB 정보와 맞는지 확인
            if (item.getItemProducts().size() != itemRequest.getProductRequests().size()) {
                throw new BusinessException(HttpResponse.Fail.PRODUCT_MISMATCH);
            }

            for (ItemProduct itemProduct : item.getItemProducts()) {
                for (ProductRequest productRequest : itemRequest.getProductRequests()) {
                    if (itemProduct.getQuantity() != productRequest.getQuantity()) {
                        throw new BusinessException(HttpResponse.Fail.PRODUCT_MISMATCH);
                    }
                }
            }

            for (ProductRequest productRequest : itemRequest.getProductRequests()) {
                // Step 3: 요청 받은 Product가 실제 DB에 존재하고 정보가 맞는지 확인
                Product product = productRepository.findByProductId(productRequest.getProductId())
                        .orElseThrow(() -> new BusinessException(HttpResponse.Fail.NOT_FOUND_PRODUCT));

                if (!product.getName().equals(productRequest.getName()) || product.getPrice().compareTo(productRequest.getPrice()) != 0) {
                    throw new BusinessException(HttpResponse.Fail.PRODUCT_MISMATCH);
                }

                // Step 4: 요청 받은 Product의 재고가 있는지 확인
                if (product.getStock() < productRequest.getQuantity()) {
                    throw new BusinessException(HttpResponse.Fail.OUT_OF_STOCK_PRODUCT);
                }

                // Step 5: Product 재고 차감
                product.minusStock(productRequest.getQuantity());
            }
        }

        // Step 6: Order 생성 및 저장
        Order order = OrderRequest.toEntity(orderRequest, calculateTotalPrice(orderRequest));
        orderRepository.save(order);

        // Step 7: OrderItem 및 OrderProduct 생성 및 저장
        for (ItemRequest itemRequest : orderRequest.getOrderItems()) {
            OrderItem orderItem = ItemRequest.toEntity(itemRequest, order);
            orderItemRepository.save(orderItem);

            for (ProductRequest productRequest : itemRequest.getProductRequests()) {
                OrderProduct orderProduct = ProductRequest.toEntity(productRequest, orderItem);
                orderProductRepository.save(orderProduct);
            }
        }

        return OrderResponse.of(order);
    }

    private BigDecimal calculateTotalPrice(OrderRequest orderRequest) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ItemRequest itemRequest : orderRequest.getOrderItems()) {
            BigDecimal itemTotalPrice = itemRequest.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(itemTotalPrice);
        }
        return totalPrice;
    }
}
