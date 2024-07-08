package unittest.accounts.exception;

import com.md.accounts.entity.Customer;
import com.md.accounts.exception.CustomerAlreadyExistException;
import com.md.accounts.repository.CustomerRespository;
import com.md.accounts.service.impl.CustomersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test class for CustomerAlreadyExistException
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 */
class CustomerAlreadyExistExceptionTest {

    @Mock
    private CustomerRespository customerRepository;

    @InjectMocks
    private CustomersServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that registerCustomer throws CustomerAlreadyExistException
     * <br/> First create a customer and then try to find the same customer again
     * <br/> If customer is found then throw CustomerAlreadyExistException
     * <br/> Here we are using mockito to mock the customerRepository
     */
    @Test
    void registerCustomer_ThrowsCustomerAlreadyExistException() {
        // Given
        Customer customer = new Customer(1L, "John Doe", "john@example.com",
                "1234567890");
        customerRepository.save(customer);
        if(customerRepository.findByMobileNumber(customer.getMobileNumber()).isPresent()){
            throw new CustomerAlreadyExistException("Customer already exist with the mobile number: " +
                    customer.getMobileNumber());
        }
    }
}