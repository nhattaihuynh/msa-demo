package com.taihuynh.ecommerce.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    Integer id,
    Integer customerId,
    List<OrderLineResponse> orderLines,
    BigDecimal totalAmount,
    String shippingAddress,
    String paymentMethod,
    OrderStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
