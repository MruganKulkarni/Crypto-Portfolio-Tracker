package com.crypto.portfolio_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioSummaryResponse {

    private BigDecimal totalInvested;

    private BigDecimal totalCurrentValue;

    private BigDecimal totalGainLoss;

    private BigDecimal totalGainLossPct;

    private List<HoldingGainResponse> holdings;

    private LocalDateTime calculatedAt;
}