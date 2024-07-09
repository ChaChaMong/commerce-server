package com.unknown.commerce.domain.product.entity;

import com.unknown.commerce.global.entity.BaseEntity;
import com.unknown.commerce.global.exception.BusinessException;
import com.unknown.commerce.global.response.HttpResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(name = "stock", columnDefinition = "BIGINT")
    private Long stock;

    public void verifyName(String name) {
        if(!this.name.equals(name)) {
            throw new BusinessException(HttpResponse.Fail.PRODUCT_MISMATCH);
        }
    }

    public void minusStock(Long quantity) {
        if(this.stock < quantity) {
            throw new BusinessException(HttpResponse.Fail.OUT_OF_STOCK_PRODUCT);
        }

        this.stock -= quantity;
    }
}
