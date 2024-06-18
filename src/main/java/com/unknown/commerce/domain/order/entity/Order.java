package com.unknown.commerce.domain.order.entity;

import com.unknown.commerce.domain.order.enumerated.OrderStatus;
import com.unknown.commerce.global.entity.BaseEntity;
import jakarta.persistence.*;
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
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "address", columnDefinition = "VARCHAR(200)")
    private String address;

    @Column(name = "address_detail", columnDefinition = "VARCHAR(200)")
    private String addressDetail;

    @Column(name = "postcode", columnDefinition = "VARCHAR(50)")
    private String postcode;

    @Column(name = "phone", columnDefinition = "BIGINT")
    private Long phone;

    @Column(name = "orderer_name", columnDefinition = "VARCHAR(200)")
    private String ordererName;

    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();
}
