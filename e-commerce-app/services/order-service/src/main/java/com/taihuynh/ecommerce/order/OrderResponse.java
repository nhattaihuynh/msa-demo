package com.taihuynh.ecommerce.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Integer id,
    String customerId,
    List<OrderLineResponse> orderLines,
    BigDecimal totalAmount,
    String paymentMethod,
    LocalDateTime createdAt
) {}
