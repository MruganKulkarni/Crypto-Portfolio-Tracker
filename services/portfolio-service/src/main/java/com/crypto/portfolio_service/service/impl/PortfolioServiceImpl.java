package com.crypto.portfolio_service.service.impl;

import com.crypto.portfolio_service.client.HoldingClient;
import com.crypto.portfolio_service.client.PriceClient;
import com.crypto.portfolio_service.dto.response.HoldingGainResponse;
import com.crypto.portfolio_service.dto.response.HoldingResponse;
import com.crypto.portfolio_service.dto.response.PortfolioSummaryResponse;
import com.crypto.portfolio_service.dto.response.PriceResponse;
import com.crypto.portfolio_service.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final HoldingClient holdingClient;

    private final PriceClient priceClient;

    @Override
    public PortfolioSummaryResponse getPortfolioSummary(Long userId) {

        List<HoldingGainResponse> holdings =
                getAllHoldings(userId);

        BigDecimal totalInvested =
                holdings.stream()
                        .map(HoldingGainResponse::getInvestedValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCurrentValue =
                holdings.stream()
                        .map(HoldingGainResponse::getCurrentValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalGainLoss =
                totalCurrentValue.subtract(totalInvested);

        BigDecimal totalGainLossPct =
                totalInvested.compareTo(BigDecimal.ZERO) == 0
                        ? BigDecimal.ZERO
                        : totalGainLoss.divide(
                        totalInvested,
                        4,
                        RoundingMode.HALF_UP
                ).multiply(BigDecimal.valueOf(100));

        return PortfolioSummaryResponse.builder()
                .totalInvested(totalInvested)
                .totalCurrentValue(totalCurrentValue)
                .totalGainLoss(totalGainLoss)
                .totalGainLossPct(totalGainLossPct)
                .holdings(holdings)
                .calculatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public List<HoldingGainResponse> getAllHoldings(Long userId) {

        List<HoldingResponse> holdings =
                holdingClient.getUserHoldings(userId);

        return holdings.stream()
                .map(this::calculateGain)
                .toList();
    }

    @Override
    public HoldingGainResponse getHoldingByCoin(
            Long userId,
            String coin
    ) {

        return getAllHoldings(userId)
                .stream()
                .filter(h ->
                        h.getCoinSymbol()
                                .equalsIgnoreCase(coin)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Coin not found"));
    }

    private HoldingGainResponse calculateGain(
            HoldingResponse holding
    ) {

        PriceResponse price = priceClient.getAllPrices()
                .stream()
                .filter(p ->
                        p.getSymbol().equalsIgnoreCase(
                                holding.getCoin()
                        )
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Price not found"));

        BigDecimal currentPrice =
                BigDecimal.valueOf(price.getCurrent_price());

        BigDecimal currentValue =
                currentPrice.multiply(holding.getQuantity());

        BigDecimal invested =
                holding.getBuyPrice()
                        .multiply(holding.getQuantity());

        BigDecimal gainLoss =
                currentValue.subtract(invested);

        BigDecimal pctChange =
                invested.compareTo(BigDecimal.ZERO) == 0
                        ? BigDecimal.ZERO
                        : gainLoss.divide(
                        invested,
                        4,
                        RoundingMode.HALF_UP
                ).multiply(BigDecimal.valueOf(100));

        return HoldingGainResponse.builder()
                .coinSymbol(holding.getCoin())
                .quantity(holding.getQuantity())
                .buyPrice(holding.getBuyPrice())
                .currentPrice(currentPrice)
                .investedValue(invested)
                .currentValue(currentValue)
                .gainLoss(gainLoss)
                .gainLossPct(pctChange)
                .build();
    }
}