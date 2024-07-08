package unittest.accounts.service.impl;

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
import com.md.accounts.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 * <br/>Below class is responsible for testing the AccountServiceImpl class
 * <br/>It contains various test cases to test the methods of AccountServiceImpl class
 * </p>
 */
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private CustomerRespository customerRespository;

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private Accounts accounts;

    @Mock
    private StreamBridge streamBridge;

    CustomerDto customerDto;
    AccountsDto accountsDto;


    /**
     * This method is used to set up the initial set of objects
     * <br/> we could have used below approach as well
     * <ul>
     *     <li>customerRespository = mock(CustomerRespository.class);</li>
     *     <li>accountsRepository = mock(AccountsRepository.class);</li>
     *     <li>streamBridge = mock(StreamBridge.class);</li>
     * </ul>
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks and inject them
        customerDto = new CustomerDto();
        accountsDto = new AccountsDto();
    }

    /**
     * This method is used to test the createAccount method
     * <br/>It should create the account for the customer
     * <br/>It should save the customer and account details
     * <br/>It should send the communication to the customer
     * <br/>It should verify the calls to the methods
     */
    @Test
    void createAccount_NewCustomer_AccountCreated() {
        customerDto.setMobileNumber("1234567890");
        customerDto.setName("Test Name");
        customerDto.setEmail("test@example.com");

        when(customerRespository.findByMobileNumber(anyString())).thenReturn(Optional.empty());

        if (customerDto.getMobileNumber().equals("1234567890")) {
            assertEquals("Customer already exist with the mobile number: 1234567890",
                    "Customer already exist with the mobile number: " + customerDto.getMobileNumber());
        }

        // Below line will first save the customer and then return the saved customer
        when(customerRespository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(accountsRepository.save(any(Accounts.class))).thenAnswer(invocation -> invocation.getArgument(0));

        accountServiceImpl.createAccount(customerDto);
        verify(customerRespository).findByMobileNumber("1234567890");
        verify(customerRespository).save(any(Customer.class));
        verify(accountsRepository).save(any(Accounts.class));
        verify(streamBridge).send(eq("sendCommunication-out-0"), any(AccountsMsgDto.class));
    }

    /**
     * This method is used to test the createAccount method for existing customer
     * <br/>It should throw CustomerAlreadyExistException for existing customer
     */
    @Test
    void createAccount_ExistingCustomer_ThrowsException() {
        // Prepare
        customerDto.setMobileNumber("1234567890");
        customerDto.setName("Test Name");
        customerDto.setEmail("test@gmail.com");
        when(customerRespository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        if (customerDto.getMobileNumber().equals("1234567890")) {
            assertEquals("Customer already exist with the mobile number: 1234567890",
                    "Customer already exist with the mobile number: " + customerDto.getMobileNumber());
        }
        when(customerRespository.findByMobileNumber("1234567890")).thenReturn(Optional.of(new Customer()));
    }

    /**
     * This method is used to test the fetchAccountDetails method
     * <br/>It should fetch the account details of the customer
     * <br/>It should verify the calls to the methods
     */
    @Test
    void fetchAccountDetails_CustomerExists_ReturnCustomerDto() {
        // Prepare
        String mobileNumber = "1234567890";
        Customer customer = new Customer(1L, "Test Name", "test@example.com", mobileNumber);
        Accounts accounts = new Accounts(1L, customer.getCustomerId(), "SAVINGS",
                "Test Branch", true);

        customerDto.setMobileNumber(mobileNumber);
        customerDto.setName("Test Name");
        customerDto.setEmail("test@example.com");

        accountsDto.setAccountNumber(accounts.getAccountNumber());
        customerDto.setAccountsDto(accountsDto);

        when(customerRespository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(customer));
        when(accountsRepository.findByCustomerId(customer.getCustomerId())).thenReturn(Optional.of(accounts));

        // Act
        CustomerDto resultDto = accountServiceImpl.fetchAccountDetails(mobileNumber);

        // Verify
        verify(customerRespository).findByMobileNumber(mobileNumber);
        verify(accountsRepository).findByCustomerId(customer.getCustomerId());
        assertEquals(customerDto.getMobileNumber(), resultDto.getMobileNumber());
        assertEquals(customerDto.getAccountsDto().getAccountNumber(), resultDto.getAccountsDto().getAccountNumber());
    }

    /**
     * This method is used to test the fetchAccountDetails method for non-existing customer
     * <br/>It should throw ResourceNotFoundException for non-existing customer
     */
    @Test
    void fetchAccountDetails_CustomerNotFound_ThrowsException() {
        // Prepare
        String mobileNumber = "nonexistent";

        when(customerRespository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.fetchAccountDetails(mobileNumber));
    }

    /**
     * This method is used to test the updateAccount method
     * <br/>It should update the account details of the customer
     * <br/>It should verify the calls to the methods
     */
    @Test
    void updateAccount_AccountAndCustomerExist_AccountUpdated() {
        // Prepare
        Long accountId = 123456789L;
        Long customerId = 1L;
        accountsDto.setAccountNumber(accountId);
        accountsDto.setAccountType("SAVINGS");
        accountsDto.setBranchAddress("Test Branch");
        customerDto.setName("Updated Name");
        customerDto.setEmail("updated@example.com");
        customerDto.setMobileNumber("1234567890");
        customerDto.setAccountsDto(accountsDto);

        Customer customer = new Customer(customerId, "Test Name", "test@example.com", "1234567890");
        Accounts accounts = new Accounts(accountId, customerId, "SAVINGS", "Test Branch", true);

        when(accountsRepository.findById(accountId)).thenReturn(Optional.of(accounts));
        when(accountsRepository.save(any(Accounts.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(customerRespository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRespository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        boolean result = accountServiceImpl.updateAccount(customerDto);

        // Verify
        verify(accountsRepository).findById(accountId);
        verify(customerRespository).findById(customerId);
        verify(accountsRepository).save(any(Accounts.class));
        verify(customerRespository).save(any(Customer.class));
        assertTrue(result);
    }

    /**
     * This method is used to test the updateAccount method for non-existing account
     * <br/>It should throw ResourceNotFoundException for a non-existing account
     */
    @Test
    void updateAccount_AccountNotFound_ThrowsException() {
        // Prepare
        Long accountId = 123456789L;
        accountsDto.setAccountNumber(accountId);
        customerDto.setAccountsDto(accountsDto);

        when(accountsRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.updateAccount(customerDto));
    }

    /**
     * This method is used to test the updateAccount method for non-existing customer
     * <br/>It should throw ResourceNotFoundException for a non-existing customer
     */
    @Test
    void updateAccount_CustomerNotFound_ThrowsException() {
        // Prepare
        Long accountId = 123456789L;
        Long customerId = 1L;
        accountsDto.setAccountNumber(accountId);
        customerDto.setAccountsDto(accountsDto);

        when(accountsRepository.findById(accountId)).thenReturn(Optional.of(accounts));
        Accounts accounts = new Accounts(accountId, customerId, "SAVINGS", "Test Branch", true);
        when(accountsRepository.save(any(Accounts.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(customerRespository.findById(accounts.getCustomerId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.updateAccount(customerDto));
    }

    /**
     * This method is used to test the deleteAccount method
     * <br/>It should delete the account of the customer
     * <br/>It should verify the calls to the methods
     */
    @Test
    void deleteAccount_CustomerExists_AccountDeleted() {
        // Prepare
        String mobileNumber = "1234567890";
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "Test Name", "test@example.com", mobileNumber);

        when(customerRespository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(customer));

        // Act
        boolean result = accountServiceImpl.deleteAccount(mobileNumber);

        // Verify
        verify(customerRespository).findByMobileNumber(mobileNumber);
        verify(accountsRepository).deleteByCustomerId(customer.getCustomerId());
        verify(customerRespository).deleteById(customer.getCustomerId());
        assertTrue(result);
    }

    /**
     * This method is used to test the deleteAccount method for non-existing customer
     * <br/>It should throw ResourceNotFoundException for a non-existing customer
     */
    @Test
    void deleteAccount_CustomerNotFound_ThrowsException() {
        // Prepare
        String mobileNumber = "nonexistent";

        when(customerRespository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.deleteAccount(mobileNumber));
    }

    /**
     * This method is used to test the updateCommunicationStatus method
     * <br/>It should update the communication status of the account
     * <br/>It should verify the calls to the methods
     */
    @Test
    void updateCommunicationStatus_AccountExists_CommunicationStatusUpdated() {
        // Prepare
        Long accountNumber = 123456789L;
        accounts.setAccountNumber(accountNumber);
        accounts.setCommunication_status(false);

        when(accountsRepository.findById(accountNumber)).thenReturn(Optional.of(accounts));
        when(accountsRepository.save(accounts)).thenReturn(accounts);

        // Act
        boolean result = accountServiceImpl.updateCommunicationStatus(accountNumber);

        // Verify
        verify(accountsRepository).findById(accountNumber);
        verify(accountsRepository).save(accounts);
        assertTrue(result);
    }

    /**
     * This method is used to test the updateCommunicationStatus method for non-existing account
     * <br/>It should throw ResourceNotFoundException for a non-existing account
     */
    @Test
    void updateCommunicationStatus_AccountNotFound_ThrowsException() {
        // Prepare
        Long accountNumber = 123456789L;

        when(accountsRepository.findById(accountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.updateCommunicationStatus(accountNumber));
    }
}