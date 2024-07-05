package com.md.spring_security_for_mdfinance.dto;


import lombok.Data;

/**
 * @Author Maulik Davra
 * @Created on July 5th 2024
 * <br/>
 * <br/>CustomerDto is a DTO class which is used to map the request body of the customer.
 * <br/> It is used to map the name, email and mobile number from the request body.
 * <br/> The CustomerDto object is used to fetch the customer details via restTemplate.
 * <br/> Please go through the AuthServiceImpl class for more details.
 *
 */
@Data
public class CustomerDto {
    private String name;
    private String email;
    private String mobileNumber;
}
