package com.unknown.commerce.domain.item.dto;

import com.unknown.commerce.domain.item.entity.ItemProduct;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ItemProductInfo {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private Long stock;

    public static ItemProductInfo of(ItemProduct itemProduct) {
        return ItemProductInfo.builder()
                .id(itemProduct.getProduct().getId())
                .name(itemProduct.getProduct().getName())
                .price(itemProduct.getProduct().getPrice())
                .quantity(itemProduct.getQuantity())
                .stock(itemProduct.getProduct().getStock())
                .build();
    }
}
