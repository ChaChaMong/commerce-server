package com.unknown.commerce.domain.item.entity;

import com.unknown.commerce.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<ItemProduct> itemProducts = new ArrayList<>();
}
