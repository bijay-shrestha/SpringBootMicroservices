package com.bijays.paymentservice.exception;

public class PaymentInformationNotFound extends RuntimeException {

    private String message;

    public PaymentInformationNotFound(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
