package com.md.accounts.mapper;

import com.md.accounts.dto.CustomerDetailsDto;
import com.md.accounts.dto.CustomerDto;
import com.md.accounts.entity.Customer;

/**
 * @Author Maulik Davra
 * @since 1.0
 * <p>
 * Below class is responsible for mapping the data from CustomerDto to Customer and vice versa.
 * </p>
 */
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

    /**
     * This method is used to map the data from Customer to CustomerDetailsDto
     * @param customer, customerDetailsDto
     * @return CustomerDetailsDto
     */
    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDto;
    }
}
