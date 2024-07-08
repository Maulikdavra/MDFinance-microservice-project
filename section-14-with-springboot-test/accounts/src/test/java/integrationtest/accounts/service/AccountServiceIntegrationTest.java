package integrationtest.accounts.service;

import com.md.accounts.controller.AccountsController;
import com.md.accounts.entity.Accounts;
import com.md.accounts.entity.Customer;
import com.md.accounts.repository.AccountsRepository;
import com.md.accounts.repository.CustomerRespository;
import com.md.accounts.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = com.md.accounts.AccountsApplication.class)
@ActiveProfiles("test")
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountServiceImpl accountsService;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRespository customerRespository;


    @Test
    void testFindByCustomerId() {
        // GIVEN
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setMobileNumber("1234567890");
        customer.setEmail("test@gmail.com");
        customer.setName("Test");

        Accounts account = new Accounts();
        account.setAccountNumber(1234567890L);
        account.setCustomerId(customerId);
        account.setAccountType("Savings");
        account.setBranchAddress("Test Branch");
        account.setCommunication_status(true);

        customerRespository.save(customer);
        accountsRepository.save(account);

        // WHEN
        Optional<Accounts> foundAccount = accountsRepository.findByCustomerId(customerId);
        Optional<Customer> foundCustomer = customerRespository.findByMobileNumber(customer.getMobileNumber());

        // THEN
        assertTrue(foundAccount.isPresent());
        assertEquals(customerId, foundAccount.get().getCustomerId());

        assertTrue(foundCustomer.isPresent());
        assertEquals(customer.getMobileNumber(), foundCustomer.get().getMobileNumber());
    }
}
