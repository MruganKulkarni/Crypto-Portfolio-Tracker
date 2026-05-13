package com.crypto.price_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(
        name = "coingecko-client",
        url = "${coingecko.api.base-url}"
)
public interface CoinGeckoClient {

    @GetMapping("/simple/price")
    Map<String, Map<String, Object>> getPrices(

            @RequestParam("ids")
            String ids,

            @RequestParam("vs_currencies")
            String currencies,

            @RequestParam("include_market_cap")
            boolean includeMarketCap
    );
}