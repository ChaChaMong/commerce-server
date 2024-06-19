package com.unknown.commerce.domain.item.dto;

import com.unknown.commerce.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ItemDetailResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<ItemProductInfo> itemProductInfos;

    public static ItemDetailResponse of(Item item) {
        List<ItemProductInfo> itemProductInfos = item.getItemProducts().stream()
                .map(ItemProductInfo::of)
                .collect(Collectors.toList());

        return ItemDetailResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .itemProductInfos(itemProductInfos)
                .build();
    }
}
