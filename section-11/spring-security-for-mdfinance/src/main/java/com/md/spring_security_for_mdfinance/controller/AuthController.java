package com.md.spring_security_for_mdfinance.controller;

import com.md.spring_security_for_mdfinance.dto.AuthRequest;
import com.md.spring_security_for_mdfinance.dto.CustomerDto;
import com.md.spring_security_for_mdfinance.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * <br/> @Author Maulik Davra
 * <br/> @Created on July 5th 2024
 * <br/>
 * <br/>This class is used to handle authentication requests.
 * <br/>It defines endpoints to fetch customer details, generate a token, and validate a token.
 * <br/>It uses the AuthService to fetch customer details and generate/validate tokens.
 * <br/>It also uses the AuthenticationManager to authenticate users.
 * <br/>The endpoints are secured using Spring Security.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * <br/>This method is used to fetch customer details.
     * <br/>It calls the fetchCustomer method of the AuthService to fetch customer details.
     * <br/> fetchCustomer method of the AuthService uses restTemplate to fetch customer details.
     * @param username of the customer
     * @return ResponseEntity<CustomerDto> containing the customer details
     */
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDto> fetchCustomer(@RequestParam String username) {
        CustomerDto CustomerDto = authService.fetchCustomer(username);
        return ResponseEntity.status(HttpStatus.OK).body(CustomerDto);
    }

    /**
     * <br/>This method is used to generate a token.
     * <br/>It takes an AuthRequest object containing the username and password.
     * <br/>It uses the AuthenticationManager to authenticate the user.
     * <br/> AuthenticationManager uses the CustomUserDetailsService to load user details.
     * <br/> Please go through the CustomUserDetailsService class for more details.
     * <br/>If the user is authenticated, it calls the generateToken method of the AuthService to generate a token.
     * @param authRequest containing the username and password
     * @return String containing the generated token
     */
    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return authService.generateToken(authRequest.getPassword());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    /**
     * <br/>This method is used to validate a token.
     * <br/>It calls the validateToken method of the AuthService to validate the token.
     * @param token to validate
     * @return String indicating the token is valid
     */
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
