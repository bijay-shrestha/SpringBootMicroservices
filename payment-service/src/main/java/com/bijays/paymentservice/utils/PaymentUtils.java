package com.bijays.paymentservice.utils;

import com.bijays.paymentservice.dto.request.PaymentRequest;
import com.bijays.paymentservice.dto.response.PaymentResponse;
import com.bijays.paymentservice.model.Payment;

import java.time.LocalDate;
import java.util.UUID;

public class PaymentUtils {

    public static Payment parsePaymentRequestToPayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setSuccess(true);
        return payment;
    }

    public static PaymentResponse parsePaymentToPaymentResponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPaymentSuccess(payment.isSuccess());
        paymentResponse.setPaymentId(payment.getPaymentId());
        paymentResponse.setMessage("Payment done successfully!");
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setOrderId(payment.getOrderId());
        return paymentResponse;
    }
}
