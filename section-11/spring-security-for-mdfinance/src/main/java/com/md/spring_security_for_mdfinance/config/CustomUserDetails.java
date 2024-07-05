package com.md.spring_security_for_mdfinance.config;

import com.md.spring_security_for_mdfinance.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @Author Maulik Davra
 * @Created on July 5th 2024
 * <br/>
 * <br/>CustomUserDetails created to implement UserDetailsService and to provide the custom implementation of UserDetails.
 * <br/> It is used to provide the user details for authentication.
 *
 */
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;

    public CustomUserDetails(Customer customer) {
        this.username = customer.getName();
        this.password = customer.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
