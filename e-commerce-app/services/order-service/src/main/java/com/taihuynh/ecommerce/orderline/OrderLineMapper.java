package com.taihuynh.ecommerce.orderline;

import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {
    
    public OrderLineResponse toResponse(OrderLine orderLine) {
        return new OrderLineResponse(
            orderLine.getId(),
            orderLine.getOrder().getId(),
            orderLine.getProductId(),
            orderLine.getQuantity()
        );
    }
}
