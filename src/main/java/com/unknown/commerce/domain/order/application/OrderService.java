package com.unknown.commerce.domain.order.application;

import com.unknown.commerce.domain.order.dto.OrderRequest;
import com.unknown.commerce.domain.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
}
