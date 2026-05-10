// PortfolioController.java

package com.crypto.portfolio_service.controller;

import com.crypto.portfolio_service.dto.response.HoldingGainResponse;
import com.crypto.portfolio_service.dto.response.PortfolioSummaryResponse;
import com.crypto.portfolio_service.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/summary")
    public PortfolioSummaryResponse getSummary(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("Authorization") String authHeader
    ) {

        return portfolioService.getPortfolioSummary(
                userId,
                authHeader
        );
    }

    @GetMapping("/holdings")
    public List<HoldingGainResponse> getHoldings(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("Authorization") String authHeader
    ) {

        return portfolioService.getAllHoldings(
                userId,
                authHeader
        );
    }

    @GetMapping("/holdings/{coin}")
    public HoldingGainResponse getHoldingByCoin(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable String coin,
            @RequestHeader("Authorization") String authHeader
    ) {

        return portfolioService.getHoldingByCoin(
                userId,
                coin,
                authHeader
        );
    }
}