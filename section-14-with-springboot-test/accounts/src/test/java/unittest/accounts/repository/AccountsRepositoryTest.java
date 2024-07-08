package unittest.accounts.repository;

import com.md.accounts.entity.Accounts;
import com.md.accounts.repository.AccountsRepository;
import com.md.accounts.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Test class for AccountsRepository
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 */
public class AccountsRepositoryTest {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountServiceImpl accountsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that findByCustomerId returns account details
     * <br/> First call findByCustomerId and then verify that it returns account details
     */
    @Test
    void testFindByCustomerId() {
        // GIVEN
        Long customerId = 1L;
        Accounts account = new Accounts();
        when(accountsRepository.findByCustomerId(customerId)).thenReturn(Optional.of(account));

        // WHEN
        Optional<Accounts> result = accountsRepository.findByCustomerId(customerId);

        // THEN
        assertNotNull(result);
        assertTrue(result.isPresent());
        verify(accountsRepository, times(1)).findByCustomerId(customerId);
    }

    /**
     * Test to verify that deleteByCustomerId deletes the account details
     * <br/> First call deleteByCustomerId and then verify that it deletes the account details
     */
    @Test
    void testDeleteByCustomerId() {
        // GIVEN
        Long customerId = 1L;
        doNothing().when(accountsRepository).deleteByCustomerId(customerId);

        // WHEN
        accountsRepository.deleteByCustomerId(customerId);

        // THEN
        verify(accountsRepository, times(1)).deleteByCustomerId(customerId);
    }
}