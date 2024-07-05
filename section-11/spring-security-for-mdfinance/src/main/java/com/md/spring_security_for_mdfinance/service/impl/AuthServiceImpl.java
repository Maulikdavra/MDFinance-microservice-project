package com.md.spring_security_for_mdfinance.service.impl;

import com.md.spring_security_for_mdfinance.dto.CustomerDto;
import com.md.spring_security_for_mdfinance.entity.Customer;
import com.md.spring_security_for_mdfinance.respository.CustomerRepository;
import com.md.spring_security_for_mdfinance.service.AuthService;
import com.md.spring_security_for_mdfinance.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * <br/> @Author Maulik Davra
 * <br/> @Created on July 5th 2024
 * <br/>
 * <br/>This class is used to provide the implementation of the AuthService.
 * <br/>It fetches customer details using restTemplate and saves them in the database.
 * <br/>It generates a token using the JwtService.
 * <br/>It validates the token using the JwtService.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * This method is used to fetch customer details.
     * <br/>It calls the fetchCustomer method of the AuthService to fetch customer details.
     * <br/>fetchCustomer method of the AuthService uses restTemplate to fetch customer details from accounts service.
     * Please go through CustomerController in accounts service for more details.
     * @param username of the customer
     * @return ResponseEntity<CustomerDto> containing the customer details
     */
    @Override
    public CustomerDto fetchCustomer(String username) {
        String url = "http://localhost:8072/mdfinance/accounts/api/fetchCustomer?username=" + username;
        ResponseEntity<CustomerDto> response = restTemplate.getForEntity(url, CustomerDto.class);
        // get the response body and save it in the database
        Optional<CustomerDto> customerDto = Optional.ofNullable(response.getBody());
        if (customerDto.isPresent()) {
            // map the customerDto to Customer entity and save it in the database
            Customer customer = new Customer();
            customer.setName(customerDto.get().getName());
            customer.setEmail(customerDto.get().getEmail());
            customer.setMobileNumber(customerDto.get().getMobileNumber());
            customer.setPassword(passwordEncoder.encode(customerDto.get().getName() + "123"));
            customerRepository.save(customer);
        }
        return response.getBody();
    }

    /**
     * This method is used to generate a token.
     * <br/>It takes a password and calls the generateToken method of the JwtService to generate a token.
     * @param Password of the customer
     * @return String containing the generated token
     */
    @Override
    public String generateToken(String Password) {
        return jwtService.generateToken(Password);
    }

    /**
     * This method is used to validate a token.
     * <br/>It takes a token and calls the validateToken method of the JwtService to validate the token.
     * @param token to be validated
     */
    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
