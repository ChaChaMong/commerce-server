package com.unknown.commerce.domain.item.entity;

import com.unknown.commerce.domain.product.entity.Product;
import com.unknown.commerce.global.entity.BaseEntity;
import com.unknown.commerce.global.exception.BusinessException;
import com.unknown.commerce.global.response.HttpResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_product")
public class ItemProduct extends BaseEntity {
    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Product product;

    public void verifyQuantity(Long quantity) {
        if(!this.quantity.equals(quantity)) {
            throw new BusinessException(HttpResponse.Fail.PRODUCT_MISMATCH);
        }
    }
}
