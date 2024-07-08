package unittest.accounts.service.impl;

import com.md.accounts.dto.*;
import com.md.accounts.entity.Accounts;
import com.md.accounts.entity.Customer;
import com.md.accounts.exception.ResourceNotFoundException;
import com.md.accounts.repository.AccountsRepository;
import com.md.accounts.repository.CustomerRespository;
import com.md.accounts.service.client.CardsFeignClient;
import com.md.accounts.service.client.LoansFeignClient;
import com.md.accounts.service.impl.CustomersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

class CustomerServiceImplTest {

    @Mock
    private CustomerRespository customerRespository;

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private CardsFeignClient cardsFeignClient;

    @Mock
    private LoansFeignClient loansFeignClient;

    @InjectMocks
    private CustomersServiceImpl customersService;

    CustomerDto customerDto;
    LoansDto loansDto;
    AccountsDto accountsDto;
    CardsDto cardsDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks and inject them
        customerDto = new CustomerDto();
        accountsDto = new AccountsDto();
        loansDto = new LoansDto();
        cardsDto = new CardsDto();
    }

    @Test
    void fetchCustomerDetails_ReturnsDetails() {
        // Prepare
        String mobileNumber = "1234567890";
        String correlationId = "correlationId";
        Customer customer = new Customer(1L, "Test Name", "test@example.com", mobileNumber);
        Accounts accounts = new Accounts(1L, 1L, "SAVINGS", "Test Branch", true);

        when(customerRespository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(customer));
        when(accountsRepository.findByCustomerId(customer.getCustomerId())).thenReturn(Optional.of(accounts));
        when(loansFeignClient.fetchLoanDetails(correlationId, mobileNumber)).thenReturn(ResponseEntity.ok(loansDto));
        when(cardsFeignClient.fetchCardDetails(correlationId, mobileNumber)).thenReturn(ResponseEntity.ok(cardsDto));

        // Act
        CustomerDetailsDto result = customersService.fetchCustomerDetails(mobileNumber, correlationId);

        // Verify
        assertNotNull(result);
        assertEquals(customer.getName(), result.getName());
        assertEquals(accounts.getAccountNumber(), result.getAccountsDto().getAccountNumber());
        assertEquals(loansDto, result.getLoansDto());
        assertEquals(cardsDto, result.getCardsDto());

        verify(customerRespository).findByMobileNumber(mobileNumber);
        verify(accountsRepository).findByCustomerId(customer.getCustomerId());
        verify(loansFeignClient).fetchLoanDetails(correlationId, mobileNumber);
        verify(cardsFeignClient).fetchCardDetails(correlationId, mobileNumber);
    }

    @Test
    void fetchCustomerDetails_ThrowsResourceNotFoundException() {
        // Prepare
        String mobileNumber = "1234567890";
        String correlationId = "correlationId";

        when(customerRespository.findByMobileNumber(mobileNumber)).thenReturn(Optional.empty());

        // Act and Verify
        assertThrows(ResourceNotFoundException.class, () -> customersService.fetchCustomerDetails(mobileNumber, correlationId));
        verify(customerRespository).findByMobileNumber(mobileNumber);
        verifyNoInteractions(accountsRepository, loansFeignClient, cardsFeignClient);
    }
}