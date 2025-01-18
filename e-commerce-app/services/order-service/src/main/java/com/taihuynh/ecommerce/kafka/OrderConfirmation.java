package com.taihuynh.ecommerce.kafka;

import com.taihuynh.ecommerce.customer.CustomerResponse;
import com.taihuynh.ecommerce.order.PaymentMethod;
import com.taihuynh.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products
) {
}
