package com.md.spring_security_for_mdfinance.config;

import com.md.spring_security_for_mdfinance.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

/**
 * <br/> @Author Maulik Davra
 * <br/> @Created on July 5th 2024
 * <br/>
 * <br/>This class is used to configure authentication in the application.
 * <br/>It defines beans for UserDetailsService, RestTemplate, PasswordEncoder, AuthenticationProvider, and AuthenticationManager.
 * <br/>It also configures the security filter chain that applies to HTTP requests.
 */
@Configuration
@EnableWebSecurity
public class AuthConfig {

    /**
     * <br/>Defines a bean for UserDetailsService.
     * <br/>This service is used to retrieve user details from a custom source (e.g., database) for authentication.
     * <br/>In this case, it returns a new instance of CustomUserDetailsService.
     * <br/> Please go through the CustomUserDetailsService class for more details.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * <br/>Configures the security filter chain that applies to HTTP requests.
     * <br/>This method disables CSRF protection (for simplicity in this example, but not recommended for production).
     * <br/>It also configures authorization rules, allowing unrestricted access to specific endpoints and requiring authentication for all others.
     * @param http HttpSecurity to configure
     * @return Configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/auth/fetchCustomerDetails", "/auth/validate", "/auth/token")
                                .permitAll()
                                .anyRequest().authenticated()
                ).build();
    }

    /**
     * <br/>Defines a bean for RestTemplate.
     * <br/>RestTemplate is used for making HTTP requests to other services.
     * <br/>This bean can be autowired and used wherever HTTP client functionality is needed.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * <br/>Defines a bean for PasswordEncoder.
     * <br/>PasswordEncoder is used to encode and verify passwords.
     * <br/>In this case, it returns a new instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <br/>Defines a bean for AuthenticationProvider.
     * <br/>AuthenticationProvider is used to authenticate users.
     * <br/>In this case, it returns a new instance of DaoAuthenticationProvider.
     * <br/>DaoAuthenticationProvider is a simple authentication provider that uses a UserDetailsService to retrieve user details.
     * <br/>It also uses a PasswordEncoder to verify passwords.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * <br/>Defines a bean for AuthenticationManager.
     * <br/>This bean is used to manage authentication processes in Spring Security.
     * <br/>It delegates authentication requests to the configured AuthenticationProvider.
     * @param config AuthenticationConfiguration used to build the AuthenticationManager
     * @return Configured AuthenticationManager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
