package com.md.accounts.functions;

import com.md.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.function.Consumer;

/**
 * <p>
 * @Author Maulik Davra
 * @CreatedOn 06/28/2024
 * @Since 1.0
 * </p>
 * <br>
 *
 *  AccountFunctions is a class holds the function that is responsible to accept the message from the message microservice.
 */
@Configuration
@Profile("!test")
public class AccountFunctions {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountFunctions.class);

    /**
     * Below, Bean will accept the message from the message microservice and update the communication for the account number.
     * @param accountsService Spring will inject the IAccountsService bean before invoking this method.
     * @return Consumer<Long> - It will return the account number.
     */
    @Bean
    public Consumer<Long> updateCommunication(IAccountsService accountsService) {
        return accountNumber -> {
            LOGGER.info("Communication updated for account number: {}", accountNumber.toString());
            accountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
