package com.taihuynh.ecommerce.payment;

import java.math.BigDecimal;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId
) {}
