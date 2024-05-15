package com.md.gatewayserver.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.http.HttpHeaders;
import java.util.List;

/**
 * @Author: Maulik Davra
 * @Date: 05-07-2024
 * @Version: 1.0
 * <p>
 *     This class holds the utility methods that are used in the RequestTraceFilter and ResponseTraceFilter classes.
 * </p>
 */
@Component
public class FilterUtility {

    public static final String CORRELATION_ID = "mdfinance-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CORRELATION_ID) != null) {
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            assert requestHeaderList != null;
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null;
        }
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

}
