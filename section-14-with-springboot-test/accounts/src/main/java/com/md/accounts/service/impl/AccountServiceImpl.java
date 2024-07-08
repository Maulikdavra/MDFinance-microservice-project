package com.md.accounts.service.impl;

import com.md.accounts.constants.AccountsConstants;
import com.md.accounts.dto.AccountsDto;
import com.md.accounts.dto.AccountsMsgDto;
import com.md.accounts.dto.CustomerDto;
import com.md.accounts.entity.Accounts;
import com.md.accounts.entity.Customer;
import com.md.accounts.exception.CustomerAlreadyExistException;
import com.md.accounts.exception.ResourceNotFoundException;
import com.md.accounts.mapper.AccountsMapper;
import com.md.accounts.mapper.CustomerMapper;
import com.md.accounts.repository.AccountsRepository;
import com.md.accounts.repository.CustomerRespository;
import com.md.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private CustomerRespository customerRespository;
    private AccountsRepository accountsRepository;
    private final StreamBridge streamBridge;


    /**
     * This method is used to create an account for the customer
     * @param customerDto - customer details
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerPhoneNumber = customerRespository.findByMobileNumber(customerDto.getMobileNumber());
        if (customerPhoneNumber.isPresent()) {
            throw new CustomerAlreadyExistException("Customer already exist with the mobile number: " +
                    customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRespository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    /**
     * This method is used to send the communication to the customer
     * @param account - account details
     * @param customer - customer details
     */
    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        LOGGER.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        LOGGER.info("Is the Communication request successfully triggered ? : {}", result);
    }

    /**
     * This method is used to fetch the account details of the customer
     * @param mobileNumber - mobile number of the customer
     * @return CustomerDto - customer details
     */
    @Override
    public CustomerDto fetchAccountDetails(String mobileNumber) {
        Customer customer = customerRespository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "mobileNumber", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * This method is used to update the account details of the customer
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRespository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRespository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the deleting of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRespository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRespository.deleteById(customer.getCustomerId());
        return true;
    }


    /**
     * This method is used to create a new account for the customer
     * @param customer - customer details
     * @return Accounts - account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    /**
     * This method is used to update the communication status of the customer
     * @param accountNumber - account number of the customer
     * @return boolean - true if communication status updated successfully
     */
    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber !=null ){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunication_status(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return  isUpdated;
    }
}
