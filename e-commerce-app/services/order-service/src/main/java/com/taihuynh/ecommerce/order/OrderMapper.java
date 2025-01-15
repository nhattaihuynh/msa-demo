package com.taihuynh.ecommerce.order;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class OrderMapper {
    
    public Order mapToOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setReference(UUID.randomUUID().toString());
        order.setTotalAmount(request.amount());
        order.setPaymentMethod(request.paymentMethod());
        return order;
    }
}
