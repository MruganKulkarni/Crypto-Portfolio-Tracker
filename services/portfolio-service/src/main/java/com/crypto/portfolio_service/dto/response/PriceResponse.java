package com.crypto.portfolio_service.dto.response;

import lombok.Data;

@Data
public class PriceResponse {

    private String id;

    private String symbol;

    private String name;

    private Double current_price;

    private Double market_cap;
}