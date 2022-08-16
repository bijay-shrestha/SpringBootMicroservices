package com.bijays.orderservice.service;


import com.bijays.orderservice.dto.request.OrderRequest;
import com.bijays.orderservice.dto.response.OrderResponse;

public interface OrderService {
    OrderResponse saveOrder(OrderRequest orderRequest);

    OrderResponse findOrderById(Integer orderId);
}
