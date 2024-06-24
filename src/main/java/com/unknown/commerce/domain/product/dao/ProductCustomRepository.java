package com.unknown.commerce.domain.product.dao;

import com.unknown.commerce.domain.product.entity.Product;

import java.util.Optional;

public interface ProductCustomRepository {
    Optional<Product> findByProductId(Long productId);
}
