package com.md.accounts.service;

import com.md.accounts.dto.CustomerDetailsDto;
import com.md.accounts.dto.CustomerDto;
import com.md.accounts.dto.CustomerDtoForSpringSecurity;

/**
 * Service to handle customer related operations
 * @Author Maulik Davra
 * @since 1.0
 */
public interface ICustomersService {
    
    /**
     * Fetch customer details by mobile number
     * @param mobileNumber of the customer
     * @return - customer details
     */
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);

    public CustomerDtoForSpringSecurity fetchCustomer(String username);
}
