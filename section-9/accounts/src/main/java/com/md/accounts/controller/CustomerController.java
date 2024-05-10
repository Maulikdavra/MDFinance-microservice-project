package com.md.accounts.controller;

import com.md.accounts.dto.CustomerDetailsDto;
import com.md.accounts.dto.CustomerDto;
import com.md.accounts.dto.ErrorResponseDto;
import com.md.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Maulik Davra
 * @since 1.0
 * <p>
 * Below class is responsible for fetching customer details, it is a REST controller class and will have all the REST APIs related to customer details.
 * <p>
 *     Please look at the CustomerDetailsDto class in the accounts module to understand the structure of the response.
 *     The response will have the customer details, account details, loan details and card details.
 * </p>
 */
@Tag(
        name = "REST APIs for Customer in MDFinance Accounts microservice",
        description = "REST APIs in MDFinance Accounts microservice to FETCH customer details"
)
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final ICustomersService customersService;

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("mdfinance-correlation-id")
                                                                       String correlationId,
                                                                   @RequestParam @Pattern(regexp="(^$|[0-9]{10})",
                                                                           message = "Mobile number must be 10 digits")
                                                                   String mobileNumber) {
        logger.info("MDFinance-Correlation-ID found: {}", correlationId);
        CustomerDetailsDto customerDetailsDto = customersService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsDto);
    }
}
