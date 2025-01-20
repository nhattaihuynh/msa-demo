package com.taihuynh.ecommerce.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    
    public Payment toEntity(PaymentRequest request) {
        return Payment.builder()
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }
    
    public PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getOrderId(),
                payment.getCreatedDate(),
                payment.getLastModifiedDate()
        );
    }
}
