package com.airlinemanagementsystem.airline_management_system.controller;

import com.airlinemanagementsystem.airline_management_system.model.Customer;
import com.airlinemanagementsystem.airline_management_system.repository.CustomerRepository;
import com.airlinemanagementsystem.airline_management_system.exception.ResourceNotFoundException;
import com.airlinemanagementsystem.airline_management_system.user.User;
import com.airlinemanagementsystem.airline_management_system.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository; // Injecting UserRepository

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + id)
        );
        return ResponseEntity.ok(customer);
    }

    // Create a new customer
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        // Fetch the User by ID
        User user = userRepository.findById(customer.getUser().getId()) // Use userRepository to fetch the user by id
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + customer.getUser().getId()));

        // Optionally, check if the user's role is null and handle it accordingly
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User's role must be set before creating a customer.");
        }

        customer.setUser(user);  // Set the existing user to the customer
        return customerRepository.save(customer); // Save the customer
    }

    // Update a customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + id)
        );

        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setPassportNumber(customerDetails.getPassportNumber());
        customer.setLoyaltyPoints(customerDetails.getLoyaltyPoints());
        customer.setFrequentFlyer(customerDetails.getFrequentFlyer());
        customer.setAddress(customerDetails.getAddress());
        customer.setCountry(customerDetails.getCountry());

        customerRepository.save(customer);
        return ResponseEntity.ok(customer);
    }

    // Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found with id: " + id)
        );
        customerRepository.delete(customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get customer statistics (example of how you might extend functionality)
    @GetMapping("/stats")
    public ResponseEntity<?> getCustomerStats() {
        long totalCustomers = customerRepository.count();
        long frequentFlyers = customerRepository.countByFrequentFlyerTrue();

        return ResponseEntity.ok(new CustomerStats(totalCustomers, frequentFlyers));
    }

    // Customer Stats Model
    private static class CustomerStats {
        private final long totalCustomers;
        private final long frequentFlyers;

        public CustomerStats(long totalCustomers, long frequentFlyers) {
            this.totalCustomers = totalCustomers;
            this.frequentFlyers = frequentFlyers;
        }

        public long getTotalCustomers() {
            return totalCustomers;
        }

        public long getFrequentFlyers() {
            return frequentFlyers;
        }
    }
}
