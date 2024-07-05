package com.md.gatewayserver.filters;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "auth/validate"
    );

    //Below predicate will check if the requested endpoint is openApiEndpoint
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

    // What is the meaning of noneMatch method?
    // Returns whether no elements of this stream match the provided predicate.
    // That means if the requested endpoint is not in the openApiEndpoints list, then it will return true and false otherwise


}
