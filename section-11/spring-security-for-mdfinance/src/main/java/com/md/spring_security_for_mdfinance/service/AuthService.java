package com.md.spring_security_for_mdfinance.service;

import com.md.spring_security_for_mdfinance.dto.CustomerDto;

/**
 * @Author Maulik Davra
 * @Created on July 5th 2024
 * <br/>
 * <br/>AuthService is a service class which is used to provide the custom implementation of the authentication service.
 * It is used to provide the customer details for authentication.
 */
public interface AuthService {

    CustomerDto fetchCustomer(String username);
    String generateToken(String Password);
    void validateToken(String token);

}
