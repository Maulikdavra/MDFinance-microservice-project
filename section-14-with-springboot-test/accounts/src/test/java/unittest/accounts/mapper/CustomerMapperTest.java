package unittest.accounts.mapper;

import com.md.accounts.dto.*;
import com.md.accounts.entity.Customer;
import com.md.accounts.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Maulik Davra
 * @since 1.0
 * <p>
 * Below class is responsible for mapping the data from CustomerDto to Customer and vice versa.
 * </p>
 */
class CustomerMapperTest {

    Customer customer;
    CustomerDto customerDto;

    AccountsDto accountsDto = new AccountsDto(1234567890L, "Savings", "Mumbai");

    LoansDto loansDto = new LoansDto("123456789", "548732457654", "Home Loan",
            100000, 1000, 99000);

    CardsDto cardsDto = new CardsDto("123456789", "100646930341","Credit Card",
            100000, 1000, 99000);

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customerDto = new CustomerDto();
    }

    /**
     * This method is used to map the data from CustomerDto to Customer
     */
    @Test
    public void testMapToCustomerDto() {
        customer.setCustomerId(1L);
        customer.setName("Maulik");
        customer.setEmail("maulikdavra06@gmail.com");
        customer.setMobileNumber("1234567890");
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        assertAll("customerDto",
                () -> assertEquals(customer.getName(), customerDto.getName()),
                () -> assertEquals(customer.getEmail(), customerDto.getEmail()),
                () -> assertEquals(customer.getMobileNumber(), customerDto.getMobileNumber())
        );
    }

    /**
     * This method is used to check the null condition for CustomerDto
     * <br/>It should throw NullPointerException and validate the message
     */
    @Test
    public void testMapToCustomerDtoNull() {
        var message = assertThrows(NullPointerException.class, () -> CustomerMapper.mapToCustomerDto(null, new CustomerDto()));
          assertEquals("Customer object is null, please check the data.", message.getMessage());
//        assertTrue(message.getMessage().contains("Customer object is null, please check the data."));
    }

    /**
     * This method is used to map the data from Customer to CustomerDto
     */
    @Test
    public void testMapToCustomer() {
        customerDto.setName("Maulik");
        customerDto.setEmail("maulikdavra06@gmail.com");
        customerDto.setMobileNumber("1234567890");
        customerDto.setAccountsDto(accountsDto);
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        assertAll("customer",
                () -> assertEquals(customerDto.getName(), customer.getName()),
                () -> assertEquals(customerDto.getEmail(), customer.getEmail()),
                () -> assertEquals(customerDto.getMobileNumber(), customer.getMobileNumber())
        );
    }

    /**
     * This method is used to check the null condition for Customer
     * <br/>It should throw NullPointerException and validate the message
     */
    @Test
    public void testMapToCustomerNull() {
        var message = assertThrows(NullPointerException.class, () -> CustomerMapper.mapToCustomer(null, new Customer()));
        assertEquals("CustomerDto object is null, please check the data.", message.getMessage());
    }

    /**
     * This method is used to map the data from Customer to CustomerDetailsDto
     */
    @Test
    public void testMapToCustomerDetailsDto() {
        customer.setCustomerId(1L);
        customer.setName("Maulik");
        customer.setEmail("maulikdavra06@gmail.com");
        customer.setMobileNumber("1234567890");
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        assertAll("customerDetailsDto",
                () -> assertEquals(customer.getName(), customerDetailsDto.getName()),
                () -> assertEquals(customer.getEmail(), customerDetailsDto.getEmail()),
                () -> assertEquals(customer.getMobileNumber(), customerDetailsDto.getMobileNumber())
        );
    }

    /**
     * This method is used to check the null condition for Customer
     * <br/>It should throw NullPointerException and validate the message
     */
    @Test
    public void testMapToCustomerDetailsDtoNull() {
        var message = assertThrows(NullPointerException.class, () -> CustomerMapper.mapToCustomerDetailsDto(null, new CustomerDetailsDto()));
        assertEquals("Customer object is null, please check the data.", message.getMessage());
    }
}