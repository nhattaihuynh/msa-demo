package com.taihuynh.ecommerce.order;

import java.math.BigDecimal;

public record OrderLineResponse(
    Integer id,
    Integer productId,
    String productName,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
) {}
