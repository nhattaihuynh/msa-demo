package com.taihuynh.ecommerce.product;

public record ProductPurchaseRequest(
    Integer productId,
    Integer quantity
) {}
