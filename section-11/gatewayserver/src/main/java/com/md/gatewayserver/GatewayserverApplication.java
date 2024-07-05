package com.md.gatewayserver;

import com.md.gatewayserver.filters.AuthenticationFilter;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

/**
 * @Author: Maulik Davra
 * @Date: 05-07-2024
 * @Version: 1.0
 */

@SpringBootApplication
public class GatewayserverApplication {

	@Autowired
	private AuthenticationFilter authenticationFilter;

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
						// before the request is forwarded to the ACCOUNTS service, check the Authentication Filter
						// if the request is authenticated, then forward the request to the ACCOUNTS service
						// add the filter below to the ACCOUNTS service that will first check if the request is authenticated
						.filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
								.rewritePath("/mdfinance/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
										.setFallbackUri("forward:/contactSupport")))
						.uri("lb://ACCOUNTS"))

				.route(p -> p
						.path("/mdfinance/loans/**")
						.filters( f -> f.rewritePath("/mdfinance/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2,
												true)))
						.uri("lb://LOANS"))


				.route(p -> p
						.path("/mdfinance/cards/**")
						.filters( f -> f.rewritePath("/mdfinance/cards/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter())
										.setKeyResolver(userKeyResolver())))
						.uri("lb://CARDS"))


				// id: spring-security-for-mdfinance
				// uri: lb://SPRING-SECURITY-FOR-MDFINANCE
				// predicates: - Path=/auth/**
				// (This is the path through which all the APIs of spring-security-for-mdfinance service can be accessed)
				.route(p -> p
						.path("/mdfinance/spring-security-for-mdfinance/**")
						.filters(f -> f.rewritePath("/mdfinance/spring-security-for-mdfinance/(?<segment>.*)","/${segment}"))
						.uri("lb://SPRING-SECURITY-FOR-MDFINANCE"))

				.build();
	}

	/**
	 *  defaultCustomizer() is used to configure the default circuit breaker configuration for the gateway server.
     *  The reason behind implementing below bean is that whenever a service is taking longer in retrying the request,
	 *  the circuit breaker will be triggered and the request will be forwarded to the fallback URI.
	 *  As the circuit breaker has less timeout duration, the request will be forwarded to the fallback URI.
	 *  <p>
	 *      The default configuration is set to timeout after 4 seconds.
	 *      <br>
	 *      The circuit breaker configuration is set to default.
	 *  </p>
	 * @return Customizer object for the ReactiveResilience4JCircuitBreakerFactory
	 */
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
	}

	/**
	 * Below, bean is used to configure the RedisRateLimiter for the gateway server.
	 * With the below configuration user is allowed to make one request per second.
	 * The RedisRateLimiter is used to limit the number of requests to the gateway server.
	 * <p>
	 *     <br>
	 *     defaultReplenishRate defines how many requests per second to allow (without any dropped requests).
	 *     This is the rate at which the token bucket is filled.
	 *     <br>
	 *     <p>
	 *     defaultBurstCapacity is the maximum number of requests a user is allowed in a single second (without any dropped requests).
	 *     This is the number of tokens the token bucket can hold. Setting this value to zero blocks all requests.
	 *     <br>
	 *     <p>
	 *     defaultRequestedTokens defines the number of tokens to consume for each request.
	 *     This is the number of tokens taken from the bucket for each request and defaults to 1
	 *
	 * @return RedisRateLimiter object
	 */
	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 1, 1);
	}

	/**
	 * <p>
	 * 		The KeyResolver is a functional interface used in Spring Cloud Gateway for rate limiting purposes.
	 * 		It's used to determine the key that will be used to identify a particular request.
	 * 		This key is then used by the rate limiter to keep track of how many requests have been made with that key.
	 * 	<br>
	 * 	<p>
	 * 		In the context of the GatewayserverApplication.java file, the userKeyResolver() method is implemented to resolve the user key based on the "user" header in the request.
	 * 		If the "user" header is not present in the request, then the user key is set to "anonymous". This means that the rate limiting will be applied per user, as identified by the "user" header.
	 * 	<br>
	 * 	<p>
	 * 		The use case for this is when you want to limit the rate of requests on a per-user basis.
	 * 		For example, you might want to prevent a single user from making too many requests in a short period of time, which could overload your server.
	 * 	<br>
	 * 	<p>
	 * 		If you don't implement a KeyResolver, the default KeyResolver provided by Spring Cloud Gateway will be used.
	 * 		The default KeyResolver uses the Principal Name from the ServerWebExchange object, which might not be suitable for your use case.
	 * 		If the Principal Name is not available, it will throw an exception. Therefore, it's often a good idea to provide a custom KeyResolver that suits your specific needs.
	 * </p>
	 * @return KeyResolver object
	 */
	@Bean
	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
				.defaultIfEmpty("anonymous");
	}
}
