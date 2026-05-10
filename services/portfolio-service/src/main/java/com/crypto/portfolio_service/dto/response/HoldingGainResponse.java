package com.crypto.portfolio_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoldingGainResponse {

    private String coinSymbol;

    private BigDecimal quantity;

    private BigDecimal buyPrice;

    private BigDecimal currentPrice;

    private BigDecimal investedValue;

    private BigDecimal currentValue;

    private BigDecimal gainLoss;

    private BigDecimal gainLossPct;
}