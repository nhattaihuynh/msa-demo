package com.taihuynh.ecommerce.customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    
    public Customer toCustomer(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .address(request.getAddress())
                .build();
    }
    
    public void updateCustomer(Customer customer, CustomerRequest request) {
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
    }
}
