package com.md.accounts.mapper;

import com.md.accounts.dto.CustomerDto;
import com.md.accounts.entity.Customer;

public class CustomerMapper {

    /**
     * This method is used to map the data from CustomerDto to Customer
     * @param customer, customerDto
     * @return CustomerDto
     */
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    /**
     * This method is used to map the data from Customer to CustomerDto
     * @param customerDto, customer
     * @return Customer
     */
    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }
}
