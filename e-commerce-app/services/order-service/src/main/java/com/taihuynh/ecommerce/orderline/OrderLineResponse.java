package com.taihuynh.ecommerce.orderline;

public record OrderLineResponse(
    Integer id,
    Integer orderId,
    Integer productId,
    Integer quantity
) {}
