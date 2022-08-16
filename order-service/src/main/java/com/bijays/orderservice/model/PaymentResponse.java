package com.bijays.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse implements Serializable {
    private Integer orderId;
    private double amount;
    private String paymentId;

    public PaymentResponse(Integer orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }
}
