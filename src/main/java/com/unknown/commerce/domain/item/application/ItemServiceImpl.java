package com.unknown.commerce.domain.item.application;

import com.unknown.commerce.domain.item.dao.ItemRepository;
import com.unknown.commerce.domain.item.dto.ItemResponse;
import com.unknown.commerce.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    @Override
    public Page<ItemResponse> getItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> items = itemRepository.findAllItems(pageable);

        return items.map(ItemResponse::of);
    }
}
