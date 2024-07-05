package com.md.spring_security_for_mdfinance.service;

import java.security.Key;
import java.util.Map;

/**
 * @Author Maulik Davra
 * @Created on July 5th 2024
 * <br/>
 * <br/>JwtService is a service class which is used to provide the custom implementation of the JWT service.
 * It is used to provide the JWT token for authentication.
 */
public interface JwtService {

    void validateToken(String token);
    String generateToken(String Password);
    String createToken(Map<String, Object> claims, String mobileNumber);
    Key getSignKey();
}
