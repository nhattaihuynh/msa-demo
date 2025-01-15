package com.taihuynh.ecommerce.order;

import com.taihuynh.ecommerce.customer.CustomerClient;
import com.taihuynh.ecommerce.exception.BusinessException;
import com.taihuynh.ecommerce.orderline.OrderLineService;
import com.taihuynh.ecommerce.product.ProductClient;
import com.taihuynh.ecommerce.product.PurchaseRequest;
import com.taihuynh.ecommerce.product.PurchaseResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.math.BigDecimal;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;

    private final Logger log = Logger.getLogger(OrderService.class.getName());

    public OrderService(CustomerClient customerClient, 
                       ProductClient productClient, 
                       OrderRepository orderRepository,
                       OrderMapper orderMapper,
                       OrderLineService orderLineService) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderLineService = orderLineService;
    }

    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Validate customer exists
        var customer = customerClient.getCustomer(orderRequest.customerId()).orElseThrow(
                () -> new BusinessException("Customer not found with id: " + orderRequest.customerId())
        );

        // Purchase the products and get product details
        var purchasedProducts = productClient.purchaseProducts(orderRequest.products());

        // Create and save the order
        var order = orderRepository.save(orderMapper.mapToOrder(orderRequest));

        // Create and save order lines
        for (PurchaseResponse product : purchasedProducts) {
            orderLineService.saveOrderLine(order, product.productId(), product.quantity());
        }

        // Start payment process (can be implemented later with payment service)
        // For now, just set the order status to PENDING

        // Send notification to kafka (can be implemented later with kafka producer)
        // For now, just log the order creation
        log.info("Order created with ID: {}", order.getId());
        
        // Fetch the complete order with order lines
        var savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new BusinessException("Order not found after creation"));
        
        return mapToOrderResponse(savedOrder);
    }

    public OrderResponse getOrder(Integer id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id: " + id));
        return mapToOrderResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByCustomer(Integer customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(Order order) {
        var orderLines = order.getOrderLines().stream()
                .map(line -> new OrderLineResponse(
                        line.getId(),
                        line.getProductId(),
                        null, // Product name will be fetched from product service if needed
                        line.getQuantity(),
                        null, // Unit price will be fetched from product service if needed
                        null  // Subtotal will be calculated if needed
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                orderLines,
                order.getTotalAmount(),
                null, // Shipping address can be added later
                order.getPaymentMethod().toString(),
                OrderStatus.PENDING,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
