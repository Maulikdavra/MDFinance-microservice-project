package com.md.spring_security_for_mdfinance.mapper;

import com.md.spring_security_for_mdfinance.dto.CustomerDto;
import com.md.spring_security_for_mdfinance.entity.Customer;

/**
 * @Author Maulik Davra
 * @Created on July 5th 2024
 * <br/>
 * <br/>CustomerMapper is a mapper class which is used to map the customer details to the database.
 * <br/>It is used to map the customer name, email and mobile number to the database.
 */
public class CustomerMapper {

    public static CustomerDto toCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer toCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
