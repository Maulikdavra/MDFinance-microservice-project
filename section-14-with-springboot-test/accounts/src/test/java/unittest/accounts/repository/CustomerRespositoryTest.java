package unittest.accounts.repository;

import com.md.accounts.entity.Customer;
import com.md.accounts.repository.CustomerRespository;
import com.md.accounts.service.impl.CustomersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for CustomerRespository
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 */
class CustomerRespositoryTest {

    @Mock
    CustomerRespository customerRespository;

    @InjectMocks
    CustomersServiceImpl customersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that findByMobileNumber returns customer details
     * <br/> First call findByMobileNumber and then verify that it returns customer details
     */
    @Test
    void testFindByMobileNumber() {
        // GIVEN
        String mobileNumber = "1234567890";
        Customer customer = new Customer();
        when(customerRespository.findByMobileNumber(mobileNumber)).thenReturn(Optional.of(customer));

        // WHEN
        Optional<Customer> result = customerRespository.findByMobileNumber(mobileNumber);

        // THEN
        assertNotNull(result);
        assertTrue(result.isPresent());
        verify(customerRespository, times(1)).findByMobileNumber(mobileNumber);
    }


}