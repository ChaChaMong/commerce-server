package com.unknown.commerce.domain.item.application;

import com.unknown.commerce.domain.item.dto.ItemDetailResponse;
import com.unknown.commerce.domain.item.dto.ItemResponse;
import org.springframework.data.domain.Page;

public interface ItemService {
    Page<ItemResponse> getItems(int page, int size);

    ItemDetailResponse getItem(Long id);
}
