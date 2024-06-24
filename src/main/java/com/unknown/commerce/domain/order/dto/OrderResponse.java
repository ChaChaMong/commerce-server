package com.unknown.commerce.domain.order.dto;

import com.unknown.commerce.domain.order.entity.Order;
import com.unknown.commerce.domain.order.enumerated.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private BigDecimal price;
    private OrderStatus status;
    private String address;
    private String addressDetail;
    private String postcode;
    private Long phone;
    private String ordererName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .price(order.getPrice())
                .status(order.getStatus())
                .address(order.getAddress())
                .addressDetail(order.getAddressDetail())
                .postcode(order.getPostcode())
                .phone(order.getPhone())
                .ordererName(order.getOrdererName())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
