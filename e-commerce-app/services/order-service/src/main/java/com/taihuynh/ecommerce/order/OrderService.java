package com.taihuynh.ecommerce.order;

import com.taihuynh.ecommerce.customer.CustomerClient;
import com.taihuynh.ecommerce.exception.BusinessException;
import com.taihuynh.ecommerce.kafka.OrderConfirmation;
import com.taihuynh.ecommerce.kafka.OrderProducer;
import com.taihuynh.ecommerce.orderline.OrderLineService;
import com.taihuynh.ecommerce.product.ProductClient;
import com.taihuynh.ecommerce.product.PurchaseResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;

    private final OrderProducer orderProducer;

    private final Logger log = Logger.getLogger(OrderService.class.getName());

    public OrderService(CustomerClient customerClient,
                        ProductClient productClient,
                        OrderRepository orderRepository,
                        OrderMapper orderMapper,
                        OrderLineService orderLineService, OrderProducer orderProducer) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
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

        // Fetch the complete order with order lines
        var savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new BusinessException("Order not found after creation"));
        
        var orderResponse = mapToOrderResponse(savedOrder);
        
        // Send order created event to Kafka
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                    orderRequest.reference(),
                    orderRequest.amount(),
                    orderRequest.paymentMethod(),
                    customer,
                    purchasedProducts
                )
        );
        // todo: start payment process
        
        return orderResponse;
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
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
                "",
                order.getCreatedAt()
        );
    }
}
