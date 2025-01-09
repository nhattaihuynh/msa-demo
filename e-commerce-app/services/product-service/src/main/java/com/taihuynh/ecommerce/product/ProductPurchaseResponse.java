package com.taihuynh.ecommerce.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
    Integer productId,
    String productName,
    Integer quantity,
    BigDecimal unitPrice
) {}
