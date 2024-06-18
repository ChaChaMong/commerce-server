package com.unknown.commerce.domain.product.entity;

import com.unknown.commerce.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseEntity {
    @Column(name = "name", columnDefinition = "VARCHAR(200)")
    private String name;

    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;
}
