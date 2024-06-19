package com.unknown.commerce.domain.item.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.unknown.commerce.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.unknown.commerce.domain.item.entity.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Item> findAllItems(Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(item.deletedAt.isNull());

        List<Item> items = jpaQueryFactory
                .select(item)
                .from(item)
                .where(builder)
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(item.count())
                .from(item)
                .where(builder);

        return PageableExecutionUtils.getPage(items, pageable, total::fetchOne);
    }

    @Override
    public Optional<Item> findByItemId(Long restaurantId) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(item.id.eq(restaurantId))
                .and(item.deletedAt.isNull());

        return Optional.ofNullable(jpaQueryFactory
                .select(item)
                .from(item)
                .where(builder)
                .fetchFirst());
    }
}
