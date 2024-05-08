package com.md.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

/**
 * @Author: Maulik Davra
 * @Date: 05-07-2024
 * @Version: 1.0
 */

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}


	/**
	 * This is the configuration for the gateway server to route the request to the respective services based on the path.
	 * <p>
	 *     The path will be replaced with the respective service name, and the response time will be added as a header.
	 *     <br>
	 *     filter() is used to replace the path/any argument after "/mdfinance/accounts/" with the /${segment}.
	 *     <br>
	 *     So at the end of the filter, the path will be /accounts/${segment} and the request will be routed to the ACCOUNTS service.
	 *     The same configuration is done for the LOANS and CARDS services.
	 * </p>
	 * <p>
	 *     Official documentation: <a href="https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/fluent-java-routes-api.html">Spring Cloud Gateway</a>
	 * </p>
	 * @param routeLocatorBuilder RouteLocatorBuilder object
	 * @return RouteLocator object
	 */

	@Bean
	public RouteLocator mdFinanceRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/mdfinance/accounts/**")
						.filters( f -> f.rewritePath("/mdfinance/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/mdfinance/loans/**")
						.filters( f -> f.rewritePath("/mdfinance/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/mdfinance/cards/**")
						.filters( f -> f.rewritePath("/mdfinance/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();

	}
}
