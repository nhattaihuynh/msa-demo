package com.taihuynh.ecommerce.customer;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Customer {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private String city;
    private String country;
    private String postalCode;
    private String password;
    private String role;
    private String status;
    private String createdDate;
    private String updatedDate;
    private String createdBy;
    private String updatedBy;

}
