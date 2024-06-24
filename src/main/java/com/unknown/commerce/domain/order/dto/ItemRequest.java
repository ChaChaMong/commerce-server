package com.unknown.commerce.domain.order.dto;

import com.unknown.commerce.domain.order.entity.Order;
import com.unknown.commerce.domain.order.entity.OrderItem;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class ItemRequest {
    private Long itemId;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private List<ProductRequest> productRequests;

    public static OrderItem toEntity(ItemRequest request, Order order) {
        return OrderItem.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .order(order)
                .build();
    }
}
