package com.md.spring_security_for_mdfinance.service.impl;

import com.md.spring_security_for_mdfinance.config.CustomUserDetails;
import com.md.spring_security_for_mdfinance.dto.CustomerDto;
import com.md.spring_security_for_mdfinance.entity.Customer;
import com.md.spring_security_for_mdfinance.respository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <br/> @Author Maulik Davra
 * <br/> @Created on July 5th 2024
 * <br/>
 * <br/>CustomUserDetailsService is a service class which is used to provide the custom implementation of UserDetailsService.
 * <br/>It is used to provide the user details for authentication.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * This method is used to load the user by username.
     * <br/>It calls the findByName method of the CustomerRepository to find the customer by name.
     * <br/>It maps the customer details to CustomUserDetails and returns it.
     * @param username of the customer
     * @return UserDetails containing the customer details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
        return customer.map(CustomUserDetails::new).orElseThrow(
                () -> new UsernameNotFoundException("Custom User did not mapped properly"));
    }
}
