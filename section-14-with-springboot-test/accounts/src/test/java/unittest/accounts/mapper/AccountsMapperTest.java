package unittest.accounts.mapper;

import com.md.accounts.dto.AccountsDto;
import com.md.accounts.entity.Accounts;
import com.md.accounts.mapper.AccountsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: Maulik Davra
 * @Created: on July 6th, 2024
 * @Description: This is the test class for AccountsMapper
 *
 */
class AccountsMapperTest {

    Accounts accounts;
    AccountsDto accountsDto;

    @BeforeEach
    void setUp() {
        accounts = new Accounts();
        accountsDto = new AccountsDto();
    }

    /**
     * This method is used to set up the data for the test cases
     * <br/>It sets accounts data and then maps it to accountsDto
     * <br/>later we are doing hard assertions to check the data
     */
    @Test
    public void testMapToAccountsDto() {
        accounts.setAccountNumber(1234567890L);
        accounts.setCustomerId(1L);
        accounts.setAccountType("Savings");
        accounts.setBranchAddress("Bangalore");
        accounts.setCommunication_status(true);
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
        assertEquals(accounts.getAccountNumber(), accountsDto.getAccountNumber());
        assertEquals(accounts.getAccountType(), accountsDto.getAccountType());
        assertEquals(accounts.getBranchAddress(), accountsDto.getBranchAddress());
    }

    /**
     * This method is used to check the null condition for Accounts
     * <br/>It should throw NullPointerException and validate the message
     */
    @Test
    public void testMapToAccountsDtoNull() {
        var message = assertThrows(NullPointerException.class, () -> AccountsMapper.mapToAccountsDto(null, new AccountsDto()));
        assertEquals("Accounts object is null, please check the data.", message.getMessage());
    }

    /**
     * This method is used to set up the data for the test cases
     * <br/>It sets accountsDto data and then maps it to accounts
     * <br/>later we are doing hard assertions to check the data
     */
    @Test
    public void testMapToAccounts() {
        accountsDto.setAccountNumber(1234567890L);
        accountsDto.setAccountType("Savings");
        accountsDto.setBranchAddress("Bangalore");
        Accounts accounts = AccountsMapper.mapToAccounts(accountsDto, new Accounts());
        assertEquals(accountsDto.getAccountNumber(), accounts.getAccountNumber());
        assertEquals(accountsDto.getAccountType(), accounts.getAccountType());
        assertEquals(accountsDto.getBranchAddress(), accounts.getBranchAddress());
    }

    /**
     * This method is used to check the null condition for AccountsDto
     * <br/>It should throw NullPointerException and validate the message
     */
    @Test
    public void testMapToAccountsNull() {
        var message = assertThrows(NullPointerException.class, () -> AccountsMapper.mapToAccounts(null, new Accounts()));
        assertEquals("AccountsDto object is null, please check the data.", message.getMessage());
    }

}