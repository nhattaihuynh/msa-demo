package com.taihuynh.ecommerce.product;

import java.math.BigDecimal;

public record ProductRequest(
    String name,
    String description,
    BigDecimal price,
    Integer quantity,
    Integer categoryId
) {}
