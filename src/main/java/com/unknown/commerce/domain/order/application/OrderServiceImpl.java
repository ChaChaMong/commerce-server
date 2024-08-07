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
            Item item = itemRepository.findByItemId(itemRequest.getItemId())
                    .orElseThrow(() -> new BusinessException(HttpResponse.Fail.NOT_FOUND_ITEM));

            // Step 1: 요청 받은 Item이 실제 DB에 존재하고 정보가 맞는지 확인
            item.verifyName(itemRequest.getName());
            item.verifyPrice(itemRequest.getPrice());
            item.verifyItemProductSize(itemRequest.getProductRequests().size());

            for (ItemProduct itemProduct : item.getItemProducts()) {
                for (ProductRequest productRequest : itemRequest.getProductRequests()) {
                    // Step 2: 요청 받은 Item의 Product 구조가 실제 DB 정보와 맞는지 확인
                    itemProduct.verifyQuantity(productRequest.getQuantity());
                }
            }

            for (ProductRequest productRequest : itemRequest.getProductRequests()) {
                Product product = productRepository.findByProductId(productRequest.getProductId())
                        .orElseThrow(() -> new BusinessException(HttpResponse.Fail.NOT_FOUND_PRODUCT));

                // Step 3: 요청 받은 Product가 실제 DB에 존재하고 정보가 맞는지 확인
                product.verifyName(productRequest.getName());

                // Step 4: Product 재고 차감
                product.minusStock(productRequest.getQuantity() * itemRequest.getQuantity());
            }
        }

        // Step 5: Order 생성 및 저장
        Order order = OrderRequest.toEntity(orderRequest);
        orderRepository.save(order);

        // Step 6: OrderItem 및 OrderProduct 생성 및 저장
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
}
