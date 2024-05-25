package com.md.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for Customer Details
 * @Author Maulik Davra
 * @since 1.0
 */
@Data
@Schema(
        name = "CustomerDetails",
        description = "This schema holds the information of the customer, cards, loans and accounts."
)
public class CustomerDetailsDto {
    @Schema(
            description = "Name of the customer",
            example = "Maulik Davra"
    )
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String name;

    @Schema(
            description = "Email of the customer",
            example = "user@md.com"
    )
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "1234567890"
    )
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountsDto accountsDto;

    @Schema(description = "Loan details of the customer")
    private LoansDto loansDto;

    @Schema(description = "Card details of the customer")
    private CardsDto cardsDto;
}
