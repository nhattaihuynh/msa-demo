package com.taihuynh.ecommerce.order;

public record OrderLineRequest(
    Integer productId,
    Integer quantity
) {}
