package com.md.accounts.mapper;

import com.md.accounts.dto.AccountsDto;
import com.md.accounts.entity.Accounts;

public class AccountsMapper {

    /**
     * This method is used to map the data from AccountsDto to Accounts
     * @param accountsDto, accounts
     * @return Accounts
     */
    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        if(accounts == null) {
            throw new NullPointerException("Accounts object is null, please check the data.");
        }
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    /**
     * This method is used to map the data from Accounts to AccountsDto
     * @param accountsDto, accounts
     * @return Accounts
     */
    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        if(accountsDto == null) {
            throw new NullPointerException("AccountsDto object is null, please check the data.");
        }
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
