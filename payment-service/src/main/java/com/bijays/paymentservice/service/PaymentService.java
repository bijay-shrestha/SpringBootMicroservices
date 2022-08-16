package com.bijays.paymentservice.service;

import com.bijays.paymentservice.dto.request.PaymentRequest;
import com.bijays.paymentservice.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse doPayment(PaymentRequest paymentRequest);

    PaymentResponse findByOrderId(Integer orderId);
}
