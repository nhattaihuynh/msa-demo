package com.taihuynh.ecommerce.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
    Integer id,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    LocalDateTime createdDate,
    LocalDateTime lastModifiedDate
) {}
