package com.md.accounts.service.client;

import com.md.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign Client to fetch card details from Cards service
 * @Author Maulik Davra
 * @since 1.0
 *
 * <p>
 * Behind the scenes, my cards FeignClient will connect with the Eureka Server and try to fetch all the instances that are registered with the logical name cards.
 * <p>
 * And once it receives one or two or any other instance details, it will try to cache those details for 30s, which is the default period.
 * <p>
 * And within these 30s it is not going to connect again with the Eureka Server, but instead it is going to leverage the details present inside the cache.
 * <p>
 * So based upon the IP details inside the cache, it is going to invoke this API along with the request which is mobile number.
 * <p>
 * So behind the scenes, all the implementation code will be generated by the open feign client.
 * </p>
 */
@FeignClient(name = "cards")
public interface CardsFeignClient {

    /**
     * Fetch card details from Cards service
     * @param mobileNumber of the customer
     * @return - card details
     */
    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestHeader("mdfinance-correlation-id") String correlationId,
                                                     @RequestParam String mobileNumber);
}
