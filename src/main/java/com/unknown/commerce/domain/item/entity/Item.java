package com.unknown.commerce.domain.item.entity;

import com.unknown.commerce.global.entity.BaseEntity;
import com.unknown.commerce.global.exception.BusinessException;
import com.unknown.commerce.global.response.HttpResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item")
public class Item extends BaseEntity {
    @Column(name = "name", columnDefinition = "VARCHAR(200)")
    private String name;

    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<ItemProduct> itemProducts = new ArrayList<>();

    public void verifyName(String name) {
        if(!this.name.equals(name)) {
            throw new BusinessException(HttpResponse.Fail.ITEM_MISMATCH);
        }
    }

    public void verifyPrice(BigDecimal price) {
        if(this.price.compareTo(price) != 0) {
            throw new BusinessException(HttpResponse.Fail.ITEM_MISMATCH);
        }
    }

    public void verifyItemProductSize(int size) {
        if(this.itemProducts.size() != size) {
            throw new BusinessException(HttpResponse.Fail.ITEM_MISMATCH);
        }
    }
}
