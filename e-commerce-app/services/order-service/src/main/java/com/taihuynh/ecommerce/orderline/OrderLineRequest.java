package com.taihuynh.ecommerce.orderline;

public record OrderLineRequest(
    Integer id,
    Integer orderId,
    Integer productId,
    Integer quantity
) {}
