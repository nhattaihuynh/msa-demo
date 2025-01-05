package com.taihuynh.ecommerce.customer;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
