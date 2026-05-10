package com.crypto.portfolio_service.service;

import com.crypto.portfolio_service.dto.response.HoldingGainResponse;
import com.crypto.portfolio_service.dto.response.PortfolioSummaryResponse;

import java.util.List;

public interface PortfolioService {

    PortfolioSummaryResponse getPortfolioSummary(Long userId);

    List<HoldingGainResponse> getAllHoldings(Long userId);

    HoldingGainResponse getHoldingByCoin(Long userId, String coin);
}