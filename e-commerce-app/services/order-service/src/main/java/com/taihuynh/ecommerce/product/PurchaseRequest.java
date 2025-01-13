package com.taihuynh.ecommerce.product;

public record PurchaseRequest(
    Integer productId,
    Integer quantity
) {
}
