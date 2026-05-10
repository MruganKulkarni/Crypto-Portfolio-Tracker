// HoldingResponse.java

package com.crypto.portfolio_service.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HoldingResponse {

    private Long id;

    private Long userId;

    private String coin;

    private BigDecimal quantity;

    private BigDecimal buyPrice;
}