package com.taihuynh.ecommerce.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/order-lines")
@RequiredArgsConstructor
public class OrderLineController {
    
    private final OrderLineService orderLineService;
    private final OrderLineMapper orderLineMapper;

    @GetMapping
    public ResponseEntity<List<OrderLineResponse>> findAll() {
        List<OrderLine> orderLines = orderLineService.findAll();
        return ResponseEntity.ok(orderLines.stream()
                .map(orderLineMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLineResponse> findById(@PathVariable Integer id) {
        OrderLine orderLine = orderLineService.getOrderLine(id);
        return ResponseEntity.ok(orderLineMapper.toResponse(orderLine));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderLineResponse>> findByOrderId(@PathVariable Integer orderId) {
        List<OrderLine> orderLines = orderLineService.findByOrderId(orderId);
        return ResponseEntity.ok(orderLines.stream()
                .map(orderLineMapper::toResponse)
                .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderLine(@PathVariable Integer id) {
        orderLineService.deleteOrderLine(id);
        return ResponseEntity.noContent().build();
    }
}
