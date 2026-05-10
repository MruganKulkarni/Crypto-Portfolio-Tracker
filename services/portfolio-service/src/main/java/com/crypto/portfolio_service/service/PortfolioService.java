// PortfolioService.java

package com.crypto.portfolio_service.service;

import com.crypto.portfolio_service.dto.response.HoldingGainResponse;
import com.crypto.portfolio_service.dto.response.PortfolioSummaryResponse;

import java.util.List;

public interface PortfolioService {

    PortfolioSummaryResponse getPortfolioSummary(
            Long userId,
            String authHeader
    );

    List<HoldingGainResponse> getAllHoldings(
            Long userId,
            String authHeader
    );

    HoldingGainResponse getHoldingByCoin(
            Long userId,
            String coin,
            String authHeader
    );
}