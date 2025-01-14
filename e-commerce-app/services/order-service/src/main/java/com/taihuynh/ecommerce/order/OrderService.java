package com.taihuynh.ecommerce.order;

import com.taihuynh.ecommerce.customer.CustomerClient;
import com.taihuynh.ecommerce.exception.BusinessException;
import com.taihuynh.ecommerce.product.ProductClient;
import org.springframework.stereotype.Service;
import java.util.List;
import java.math.BigDecimal;

@Service
public class OrderService {
    
    private final CustomerClient customerClient;

    private final ProductClient productClient;

    public OrderService(CustomerClient customerClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
    }


    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Validate customer exists
        var customer = customerClient.getCustomer(orderRequest.customerId()).orElseThrow(
                () -> new BusinessException("Customer not found with id: " + orderRequest.customerId())
        );

        // purchase the products

        // persist order

        // persist order line

        // start payment process

        // send notification to kafka
        
        return null;
    }

    public OrderResponse getOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        return mapToOrderResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    public List<OrderResponse> getOrdersByCustomer(Integer customerId) {
        validateCustomer(customerId);
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    public OrderResponse updateOrder(Integer id, OrderRequest orderRequest) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        
        // Only allow updates if order is in PENDING state
        if (existingOrder.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Can only update orders in PENDING state");
        }
        
        validateCustomer(orderRequest.customerId());
        validateAndReserveProducts(orderRequest.orderLines());
        
        // Update order
        Order updatedOrder = mapToOrder(orderRequest);
        updatedOrder.setId(id);
        updatedOrder = orderRepository.save(updatedOrder);
        
        return mapToOrderResponse(updatedOrder);
    }

    public void deleteOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        
        // Only allow deletion if order is in PENDING state
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Can only delete orders in PENDING state");
        }
        
        orderRepository.delete(order);
    }

    public OrderResponse updateOrderStatus(Integer id, OrderStatusRequest statusRequest) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        
        // Validate status transition
        validateStatusTransition(order.getStatus(), statusRequest.status());
        
        order.setStatus(statusRequest.status());
        Order updatedOrder = orderRepository.save(order);
        
        return mapToOrderResponse(updatedOrder);
    }

    private void validateCustomer(Integer customerId) {
        try {
            restTemplate.getForObject(customerUrl + "/" + customerId, Object.class);
        } catch (Exception e) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
    }

    private void validateAndReserveProducts(List<OrderLineRequest> orderLines) {
        for (OrderLineRequest line : orderLines) {
            try {
                // Call product service to validate and reserve
                restTemplate.postForObject(
                    productUrl + "/purchase",
                    new ProductPurchaseRequest(line.productId(), line.quantity()),
                    Object.class
                );
            } catch (Exception e) {
                throw new ProductValidationException("Failed to validate/reserve product: " + line.productId());
            }
        }
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // Implement status transition validation logic
        // Example: Can't move from DELIVERED to PROCESSING
        if (currentStatus == OrderStatus.DELIVERED && 
            (newStatus == OrderStatus.PROCESSING || newStatus == OrderStatus.CONFIRMED)) {
            throw new IllegalStateException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }
        // Add more transition rules as needed
    }

    private Order mapToOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setShippingAddress(request.shippingAddress());
        order.setPaymentMethod(request.paymentMethod());
        order.setStatus(OrderStatus.PENDING);
        
        List<OrderLine> orderLines = request.orderLines().stream()
            .map(this::mapToOrderLine)
            .toList();
        order.setOrderLines(orderLines);
        
        return order;
    }

    private OrderLine mapToOrderLine(OrderLineRequest request) {
        // Get product details from product service
        ProductResponse product = restTemplate.getForObject(
            productUrl + "/" + request.productId(),
            ProductResponse.class
        );
        
        OrderLine orderLine = new OrderLine();
        orderLine.setProductId(request.productId());
        orderLine.setQuantity(request.quantity());
        orderLine.setProductName(product.name());
        orderLine.setUnitPrice(product.price());
        orderLine.setSubtotal(product.price().multiply(BigDecimal.valueOf(request.quantity())));
        
        return orderLine;
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getCustomerId(),
            mapToOrderLineResponses(order.getOrderLines()),
            calculateTotalAmount(order.getOrderLines()),
            order.getShippingAddress(),
            order.getPaymentMethod(),
            order.getStatus(),
            order.getCreatedAt(),
            order.getUpdatedAt()
        );
    }

    private List<OrderLineResponse> mapToOrderLineResponses(List<OrderLine> orderLines) {
        return orderLines.stream()
            .map(line -> new OrderLineResponse(
                line.getId(),
                line.getProductId(),
                line.getProductName(),
                line.getQuantity(),
                line.getUnitPrice(),
                line.getSubtotal()
            ))
            .toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderLine> orderLines) {
        return orderLines.stream()
            .map(OrderLine::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
