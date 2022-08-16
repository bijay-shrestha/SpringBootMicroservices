package com.bijays.orderservice.utils;

import com.bijays.orderservice.dto.request.OrderRequest;
import com.bijays.orderservice.dto.response.OrderResponse;
import com.bijays.orderservice.model.Order;
import com.bijays.orderservice.model.PaymentResponse;

public class OrderUtils {

    public static Order parseOrderRequestToOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setName(orderRequest.getName());
        order.setQuantity(orderRequest.getQuantity());
        order.setAmount(orderRequest.getAmount());
        return order;
    }

    public static OrderResponse parseOrderToOrderResponse(Order order, PaymentResponse payment){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setName(order.getName());
        orderResponse.setQuantity(order.getQuantity());
        orderResponse.setAmount(order.getAmount());
        orderResponse.setTransactionId(payment.getPaymentId());
        orderResponse.setMessage("Order placed successfully!");
        return orderResponse;
    }
}
