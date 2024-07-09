package com.unknown.commerce.domain.order.dto;

import com.unknown.commerce.domain.order.entity.Order;
import com.unknown.commerce.domain.order.enumerated.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class OrderRequest {
    private List<ItemRequest> orderItems;
    private OrderStatus status;
    private String address;
    private String addressDetail;
    private String postcode;
    private Long phone;
    private String ordererName;

    public static Order toEntity(OrderRequest request) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ItemRequest itemRequest : request.getOrderItems()) {
            BigDecimal itemTotalPrice = itemRequest.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalPrice = totalPrice.add(itemTotalPrice);
        }

        return Order.builder()
                .price(totalPrice)
                .status(request.getStatus())
                .address(request.getAddress())
                .addressDetail(request.getAddressDetail())
                .postcode(request.getPostcode())
                .phone(request.getPhone())
                .ordererName(request.getOrdererName())
                .build();
    }
}
