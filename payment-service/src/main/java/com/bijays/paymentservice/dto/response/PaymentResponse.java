package com.bijays.paymentservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse implements Serializable {
    private String message;
    private String paymentId;
    private Integer orderId;
    private double amount;
    private boolean isPaymentSuccess;
}
