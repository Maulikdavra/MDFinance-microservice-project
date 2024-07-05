package com.md.spring_security_for_mdfinance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Maulik Davra
 * @Created on July 5th 2024
 * <br/>
 * <br/>AuthRequest is a DTO class which is used to map the request body of the login request.
 * <br/> It is used to map the username and password from the request body.
 * <br/> The AuthRequest object is used to authenticate the user.
 * <br/> Please go through the AuthController class for more details.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String username;
    private String password;

}
