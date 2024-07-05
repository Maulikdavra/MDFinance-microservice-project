package com.md.message.function;

import com.md.message.dto.AccountsMsgDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
import java.util.logging.Logger;

@Configuration
public class MessageFunctions {
    private static final Logger LOGGER = Logger.getLogger(MessageFunctions.class.getName());

    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email(){
        return accountsMsgDto -> {
            LOGGER.info("Sending email with details "+accountsMsgDto.toString());
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto, Long> sms(){
        return accountsMsgDto -> {
            LOGGER.info("Sending sms with details "+accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
        };
    }

}
