package com.crypto.holding.dto.request;

import com.crypto.holding.constants.CoinSymbol;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddHoldingRequest {

    @NotNull(message = "Coin symbol is required")
    private CoinSymbol coin;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.00000001", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Buy price is required")
    @DecimalMin(value = "0.01", message = "Buy price must be greater than 0")
    private BigDecimal buyPrice;

    @NotNull(message = "Bought date is required")
    private LocalDateTime boughtAt;
}