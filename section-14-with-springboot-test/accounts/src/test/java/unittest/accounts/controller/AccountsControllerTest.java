package unittest.accounts.controller;

import com.md.accounts.constants.AccountsConstants;
import com.md.accounts.controller.AccountsController;
import com.md.accounts.dto.AccountsDto;
import com.md.accounts.dto.CustomerDto;
import com.md.accounts.dto.ResponseDto;
import com.md.accounts.service.IAccountsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AccountsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAccountsService accountsService;

    @InjectMocks
    private AccountsController accountsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(accountsController).build();
    }

    @Test
    public void testCreateAccount() throws Exception {
        // GIVEN
        CustomerDto customerDto = new CustomerDto();
        AccountsDto accountsDto = new AccountsDto(1234567890L, "Savings", "Mumbai");
        customerDto.setName("Maulik");
        customerDto.setEmail("test@gmail.com");
        customerDto.setMobileNumber("1234567890");
        customerDto.setAccountsDto(accountsDto);

        doNothing().when(accountsService).createAccount(customerDto);

        // WHEN & THEN
        mockMvc.perform(post("/api/create")
                        .contentType("application/json")
                        .content("{\"name\":\"Maulik\",\"email\":\"test@gmail.com\",\"mobileNumber\":\"1234567890\"," +
                                "\"accountsDto\":{\"accountNumber\":1234567890,\"accountType\":\"Savings\"," +
                                "\"branchAddress\":\"Mumbai\"}}")) // replace with actual fields
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.statusCode").value("201"))
                .andExpect(jsonPath("$.statusMessage").value("Account created successfully"));
    }

}