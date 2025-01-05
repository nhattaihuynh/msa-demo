package com.taihuynh.ecommerce.customer;

import com.taihuynh.ecommerce.exception.CustomerNotFoundException;
import com.taihuynh.ecommerce.exception.DuplicateEmailException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    
    public Customer createCustomer(@Valid CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + request.getEmail());
        }
        
        Customer customer = customerMapper.toCustomer(request);
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(String id, @Valid CustomerRequest request) {
        Customer existingCustomer = getCustomerById(id);
        
        // Check if email is being changed and if new email already exists
        if (!existingCustomer.getEmail().equals(request.getEmail()) && 
            customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + request.getEmail());
        }
        
        customerMapper.updateCustomer(existingCustomer, request);
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    public boolean checkCustomerExists(String id) {
        return customerRepository.existsById(id);
    }
}
