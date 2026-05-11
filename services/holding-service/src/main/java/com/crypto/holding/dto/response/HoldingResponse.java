package com.crypto.holding.dto.response;

import com.crypto.holding.constants.CoinSymbol;
import com.crypto.holding.constants.HoldingStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingResponse {

    private Long id;
    private Long userId;
    private CoinSymbol coin;
    private BigDecimal quantity;
    private BigDecimal buyPrice;
    private HoldingStatus status;
    private LocalDateTime boughtAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}