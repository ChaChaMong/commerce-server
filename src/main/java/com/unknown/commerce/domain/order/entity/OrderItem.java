package com.unknown.commerce.domain.order.entity;

import com.unknown.commerce.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item")
public class OrderItem extends BaseEntity {
    @Column(name = "name", columnDefinition = "VARCHAR(200)")
    private String name;

    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @OneToMany(mappedBy = "orderItem")
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
