package com.taihuynh.ecommerce.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "customer-service", url = "${application.customer-service.url}")
public interface CustomerClient {
    
    @GetMapping("/{customerId}")
    Optional<CustomerResponse> getCustomer(@PathVariable("customerId") String customerId);
    
}
