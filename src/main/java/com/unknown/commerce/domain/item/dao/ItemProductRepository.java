package com.unknown.commerce.domain.item.dao;

import com.unknown.commerce.domain.item.entity.ItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemProductRepository extends JpaRepository<ItemProduct, Long> {
}
