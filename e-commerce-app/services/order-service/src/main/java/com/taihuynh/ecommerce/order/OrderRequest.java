package com.taihuynh.ecommerce.order;

import com.taihuynh.ecommerce.product.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
    String id,
    String customerId,
    String reference,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    List<PurchaseRequest> products
) {}
