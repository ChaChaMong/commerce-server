package com.unknown.commerce.domain.item.dto;

import com.unknown.commerce.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ItemResponse {
    private Long id;
    private String name;
    private BigDecimal price;

    public static ItemResponse of(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }
}
