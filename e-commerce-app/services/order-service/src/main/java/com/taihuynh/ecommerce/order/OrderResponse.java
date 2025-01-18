package com.taihuynh.ecommerce.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Integer id,
    String reference,
    String customerId,
    BigDecimal totalAmount,
    String paymentMethod,
    OrderStatus status,
    List<OrderLineResponse> orderLines,
    LocalDateTime createdAt
) {}
