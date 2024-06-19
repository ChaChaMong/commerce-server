package com.unknown.commerce.domain.item.dao;

import com.unknown.commerce.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemCustomRepository {
    Page<Item> findAllItems(Pageable pageable);

    Optional<Item> findByItemId(Long itemId);
}
