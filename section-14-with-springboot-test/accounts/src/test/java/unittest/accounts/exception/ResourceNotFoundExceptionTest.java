package unittest.accounts.exception;

import com.md.accounts.entity.Customer;
import com.md.accounts.exception.ResourceNotFoundException;
import com.md.accounts.repository.CustomerRespository;
import com.md.accounts.service.impl.CustomersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ResourceNotFoundException
 *
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 */
class ResourceNotFoundExceptionTest {

    @Mock
    private CustomerRespository customerRepository;

    @InjectMocks
    private CustomersServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that registerCustomer throws ResourceNotFoundException
     * <br/> First try to find a customer with a mobile number
     * <br/> If customer is not found then throw ResourceNotFoundException
     * <br/> Here we are using mockito to mock the customerRepository
     */
    @Test
    void registerCustomer_ThrowsResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            customerRepository.findByMobileNumber("1234567890").orElseThrow(
                            () -> new ResourceNotFoundException("Resource not found with the mobile number: 1234567890",
                                    "mobileNumber", "1234567890"));
        });
    }

}