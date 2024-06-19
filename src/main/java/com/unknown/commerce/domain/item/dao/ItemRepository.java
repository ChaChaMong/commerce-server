package com.unknown.commerce.domain.item.dao;

import com.unknown.commerce.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemCustomRepository{
}
