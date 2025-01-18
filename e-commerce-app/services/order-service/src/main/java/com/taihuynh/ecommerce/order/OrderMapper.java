package com.taihuynh.ecommerce.order;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
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

    public OrderResponse toResponse(Order order) {
        List<OrderLineResponse> orderLineResponses = order.getOrderLines().stream()
            .map(line -> new OrderLineResponse(
                line.getId(),
                line.getProductId(),
                line.getQuantity(),
                line.getPrice()
            ))
            .collect(Collectors.toList());

        return new OrderResponse(
            order.getId(),
            order.getReference(),
            order.getCustomerId(),
            order.getTotalAmount(),
            order.getPaymentMethod(),
            order.getStatus(),
            orderLineResponses,
            order.getCreatedAt()
        );
    }
}
