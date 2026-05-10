// PriceClient.java

package com.crypto.portfolio_service.client;

import com.crypto.portfolio_service.dto.response.PriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "PRICE-SERVICE", path = "/prices")
public interface PriceClient {

    @GetMapping
    List<PriceResponse> getAllPrices();
}