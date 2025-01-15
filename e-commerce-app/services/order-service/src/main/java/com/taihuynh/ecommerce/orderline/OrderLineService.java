package com.taihuynh.ecommerce.orderline;

import com.taihuynh.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public OrderLine saveOrderLine(Order order, Integer productId, Integer quantity) {
        OrderLine orderLine = OrderLine.builder()
                .order(order)
                .productId(productId)
                .quantity(quantity)
                .build();
        return orderLineRepository.save(orderLine);
    }

    public OrderLine getOrderLine(Integer id) {
        return orderLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderLine not found with id: " + id));
    }
}
