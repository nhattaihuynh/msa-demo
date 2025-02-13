package com.taihuynh.ecommerce.notification;

import com.taihuynh.ecommerce.kafka.order.OrderConfirmation;
import com.taihuynh.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    private String id;
    private LocalDateTime notificationDate;
    private NotificationType notificationType;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
