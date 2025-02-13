package com.taihuynh.ecommerce.payment;

import com.taihuynh.ecommerce.notification.NotificationProducer;
import com.taihuynh.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;
    
    public Integer createPayment(PaymentRequest request) {
        Payment payment = paymentMapper.toEntity(request);
        Payment savedPayment = paymentRepository.save(payment);
        notificationProducer.sendNotification(new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstName(),
                request.customer().lastName(),
                request.customer().email()
        ));
        return savedPayment.getId();
    }
}
