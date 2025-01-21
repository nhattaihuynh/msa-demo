package com.taihuynh.ecommerce.notification;

import com.taihuynh.ecommerce.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerEmail,
        String customerFirstName,
        String customerLastName
) {
}
