package com.md.accounts.service.impl;

import com.md.accounts.dto.*;
import com.md.accounts.entity.Accounts;
import com.md.accounts.entity.Customer;
import com.md.accounts.exception.ResourceNotFoundException;
import com.md.accounts.mapper.AccountsMapper;
import com.md.accounts.mapper.CustomerMapper;
import com.md.accounts.repository.AccountsRepository;
import com.md.accounts.repository.CustomerRespository;
import com.md.accounts.service.ICustomersService;
import com.md.accounts.service.client.CardsFeignClient;
import com.md.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @Author Maulik Davra
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService{

    private AccountsRepository accountsRepository;
    private CustomerRespository customerRespository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * Method to fetch customer details based on mobile number
     * @param mobileNumber of the customer
     * @return CustomerDetailsDto
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRespository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "mobileNumber", customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        // Fetching loans details using Feign client
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        // Fetching cards details using a Feign client
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
