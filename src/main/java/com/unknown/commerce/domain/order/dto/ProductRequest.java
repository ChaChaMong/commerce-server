package com.unknown.commerce.domain.order.dto;

import com.unknown.commerce.domain.order.entity.OrderItem;
import com.unknown.commerce.domain.order.entity.OrderProduct;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductRequest {
    private Long productId;
    private String name;
    private BigDecimal price;
    private Long quantity;

    public static OrderProduct toEntity(ProductRequest request, OrderItem orderItem) {
        return OrderProduct.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .orderItem(orderItem)
                .build();
    }
}
