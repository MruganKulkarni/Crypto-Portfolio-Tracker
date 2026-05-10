// HoldingClient.java

package com.crypto.portfolio_service.client;

import com.crypto.portfolio_service.dto.response.HoldingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "HOLDING-SERVICE", path = "/api/holdings")
public interface HoldingClient {

    @GetMapping
    List<HoldingResponse> getUserHoldings(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("Authorization") String authHeader
    );
}