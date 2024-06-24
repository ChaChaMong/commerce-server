package com.unknown.commerce.domain.product.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerce.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.unknown.commerce.domain.product.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Product> findByProductId(Long productId) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(product.id.eq(productId))
                .and(product.deletedAt.isNull());

        return Optional.ofNullable(jpaQueryFactory
                .select(product)
                .from(product)
                .where(builder)
                .fetchFirst());
    }
}
