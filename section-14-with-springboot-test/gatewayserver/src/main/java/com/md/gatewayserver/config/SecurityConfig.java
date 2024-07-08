package com.md.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * <p>
 * The @EnableWebFluxSecurity annotation is used to enable WebFlux support in Spring Security.
 * It is a marker annotation that tells Spring to automatically apply the Spring Security configuration to the global WebSecurity.
 * This annotation is used to provide WebFlux security support in a Spring Boot application.
 * <br>
 * <br>
 * When you add @EnableWebFluxSecurity to a configuration class, Spring Security’s SecurityWebFilterChain
 * provides some convenient defaults to get your application up and running quickly.
 * This includes authentication and authorization features, such as login forms and permission checks.
 * </p>
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * <p>
     *     <ul>
     *         <li>The SecurityWebFilterChain is a filter chain that is capable of processing WebFilter instances.</li>
     *         <li>It is used to secure the application by building a security filter chain.</li>
     *         <li>The SecurityWebFilterChain is responsible for creating a WebFilter chain that is capable of processing WebFilter instances.</li>
     *         <br>
     *         <li>Here we are allowing all GET requests to pass through without any security checks.</li>
     *         <li>For POST requests, we are checking the roles of the user.</li>
     *         <br>
     *         <li>The oauth2ResourceServer() method is used to configure the OAuth 2.0 Resource Server.</li>
     *         <li>It is used to configure the JWT authentication mechanism.</li>
     *         <br>
     *         <li>The jwt() method is used to configure the JWT authentication mechanism.</li>
     *         <li>We are using the jwtAuthenticationConverter() method to extract the granted authorities from the JWT token.</li>
     *         <br>
     *         <li>The csrf() method is used to disable the CSRF protection for development purposes.</li>
     *     </ul>
     * @param serverHttpSecurity is used to build the security filter chain.
     * @return the security filter chain.
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers("/mdfinance/accounts/**").hasRole("ACCOUNTS")
                        .pathMatchers("/mdfinance/cards/**").hasRole("CARDS")
                        .pathMatchers("/mdfinance/loans/**").hasRole("LOANS"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return serverHttpSecurity.build();
    }

    /**
     * <p>
     *     The grantedAuthoritiesExtractor() method is used to extract the granted authorities from the JWT token.
     *     We are using the ReactiveJwtAuthenticationConverterAdapter class to convert the JWT token to an AbstractAuthenticationToken.
     * </p>
     * @return the granted authorities' extractor.
     */
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
