package com.bijays.paymentservice.service.impl;

import com.bijays.paymentservice.dto.request.PaymentRequest;
import com.bijays.paymentservice.dto.response.PaymentResponse;
import com.bijays.paymentservice.exception.PaymentInformationNotFound;
import com.bijays.paymentservice.model.Payment;
import com.bijays.paymentservice.repository.PaymentRepository;
import com.bijays.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

import static com.bijays.paymentservice.utils.PaymentUtils.parsePaymentRequestToPayment;
import static com.bijays.paymentservice.utils.PaymentUtils.parsePaymentToPaymentResponse;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public PaymentResponse doPayment(PaymentRequest paymentRequest) {
        Payment payment = parsePaymentRequestToPayment(paymentRequest);
        paymentRepository.save(payment);
        return parsePaymentToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse findByOrderId(Integer orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId);
        if(payment==null){
            throw new PaymentInformationNotFound("Payment Information Not Found");
        }
        return parsePaymentToPaymentResponse(payment);
    }
}
