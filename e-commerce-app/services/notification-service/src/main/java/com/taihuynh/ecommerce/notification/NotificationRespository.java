package com.taihuynh.ecommerce.notification;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRespository extends MongoRepository<Notification, String> {
}
