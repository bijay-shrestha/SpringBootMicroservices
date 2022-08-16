package com.bijays.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {
    private String name;
    private Integer quantity;
    private double amount;
    private String message;
    private String transactionId;

    public OrderResponse(String message) {
        this.message = message;
    }
}
