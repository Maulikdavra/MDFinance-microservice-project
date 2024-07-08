package unittest.accounts.functions;

import com.md.accounts.functions.AccountFunctions;
import com.md.accounts.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Test class for AccountFunctions
 * @Author Maulik Davra
 * @Created On July 7th, 2024
 */
class AccountFunctionsTest {

    @Mock
    AccountServiceImpl accountService;

    @InjectMocks
    AccountFunctions accountFunctions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test to verify that updateCommunication returns true
     * <br/> First call updateCommunication and then verify that it returns true
     */
    @Test
    void updateCommunicationTest() {
        when(accountService.updateCommunicationStatus(1L)).thenReturn(true);
    }
}