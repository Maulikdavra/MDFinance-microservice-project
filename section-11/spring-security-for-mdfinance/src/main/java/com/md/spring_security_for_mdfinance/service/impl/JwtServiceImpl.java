package com.md.spring_security_for_mdfinance.service.impl;

import com.md.spring_security_for_mdfinance.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <br/> @Author Maulik Davra
 * <br/> @Created on July 5th 2024
 * <br/>
 * <br/>JwtServiceImpl is a service class which is used to provide the implementation of the JwtService.
 * <br/>It is used to generate and validate the JWT token.
 */
@Service
public class JwtServiceImpl implements JwtService {

    // This is a secret key used to sign the JWT token. This key is used to verify the integrity of the token.
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * This method is used to validate the token.
     * <br/>It uses the Jwts.parserBuilder() method to parse the token.
     * <br/>It sets the signing key using the getSignKey() method.
     * <br/>It builds the parser and parses the token.
     * <br/>Basically, it validates the token by parsing it.
     * @param token - JWT token
     */
    @Override
    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    /**
     * This method is used to generate the token.
     * <br/>It creates a map of claims.
     * <br/>It calls the createToken method to create the token.
     * @param Password - of the customer
     * @return JWT token
     */
    @Override
    public String generateToken(String Password) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, Password);
    }

    /**
     * This method is used to create the token.
     * <br/>It sets the claims, subject, issuedAt, expiration, and signing key.
     * <br/>It builds the token and returns it.
     * @param claims - map of claims
     * @param Name - of the customer
     * @return JWT token
     */
    @Override
    public String createToken(Map<String, Object> claims, String Name) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(Name)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * This method is used to get the signing key.
     * <br/>It decodes the secret key and returns the HMAC SHA key for the key bytes.
     * @return Key
     */
    @Override
    public Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
