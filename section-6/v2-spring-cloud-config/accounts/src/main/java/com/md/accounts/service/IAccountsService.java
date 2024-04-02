package com.md.accounts.service;

import com.md.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * This method is used to create an account for the customer
     * @param customerDto - customer details
     */
    void createAccount(CustomerDto customerDto);

    /**
     * This method is used to fetch the account details of the customer
     * @param mobileNumber - mobile number of the customer
     * @return CustomerDto - customer details
     */
    CustomerDto fetchAccountDetails(String mobileNumber);

    /**
     * This method is used to update the account details of the customer
     * @param customerDto - customer details
     * @return boolean - true if account details updated successfully
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * This method is used to delete the account details of the customer
     * @param mobileNumber - mobile number of the customer
     * @return boolean - true if account details deleted successfully
     */
    boolean deleteAccount(String mobileNumber);
}
