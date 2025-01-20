package com.taihuynh.ecommerce.orderline;

import com.taihuynh.ecommerce.order.Order;
import com.taihuynh.ecommerce.exception.BusinessException;
import org.springframework.stereotype.Service;
import java.util.List;

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
                .orElseThrow(() -> new BusinessException("OrderLine not found with id: " + id));
    }

    public List<OrderLine> findAll() {
        return orderLineRepository.findAll();
    }

    public List<OrderLine> findByOrderId(Integer orderId) {
        return orderLineRepository.findByOrderId(orderId);
    }

    public void deleteOrderLine(Integer id) {
        if (!orderLineRepository.existsById(id)) {
            throw new BusinessException("OrderLine not found with id: " + id);
        }
        orderLineRepository.deleteById(id);
    }
}
