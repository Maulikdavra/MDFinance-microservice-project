package com.md.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Maulik Davra
 * @Date: 05-07-2024
 * @Version: 1.0
 *
 * <p>
 *     This filter is responsible to generate a trace ID or correlation ID whenever a new request comes to gateway
 *     server via external client applications.
 * </p>
 */
@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    /**
     * Mono represents a single value or no value at all. It is a publisher that publishes at most one value and then completes.
     * <p>
     *     This method is responsible to generate a correlation ID if it is not present in the request headers.
     *     <br>
     *     If the correlation ID is present in the request headers, then it will log the correlation ID.
     *     <br>
     *     If the correlation ID is not present in the request headers, then it will generate a new correlation ID and log it.
     *     The correlation ID is generated using the UUID class.
     *     <br>
     *     The correlation ID is set in the request headers using the setCorrelationId() method of the FilterUtility class.
     *     <br>
     *     GatewayFilterChain is used to pass next filter in the chain. ServerWebExchange is used to get and set the request headers.
     * </p>
     *
     * </p>
     * @param exchange is the request and response object.
     * @param chain is used to pass the request to the next filter in the chain.
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(requestHeaders)) {
            logger.debug("mdfinance-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(requestHeaders));
        } else {
            String correlationID = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationID);
            logger.debug("mdfinance-correlation-id generated in RequestTraceFilter : {}", correlationID);
        }
        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (filterUtility.getCorrelationId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

}
