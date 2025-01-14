package com.taihuynh.ecommerce.product;

import java.math.BigDecimal;

public record PurchaseResponse(
    Integer productId,
    Integer quantity,
    BigDecimal price,
    String name,
    String description
) {
}
