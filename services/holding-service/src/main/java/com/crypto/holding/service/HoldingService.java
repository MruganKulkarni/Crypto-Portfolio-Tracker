package com.crypto.holding.service;

import com.crypto.holding.dto.request.AddHoldingRequest;
import com.crypto.holding.dto.request.UpdateHoldingRequest;
import com.crypto.holding.dto.response.HoldingResponse;
import java.util.List;

public interface HoldingService {

    HoldingResponse addHolding(Long userId, AddHoldingRequest request);

    List<HoldingResponse> getAllHoldings(Long userId);

    HoldingResponse getHoldingById(Long userId, Long holdingId);

    HoldingResponse updateHolding(Long userId, Long holdingId, UpdateHoldingRequest request);

    void deleteHolding(Long userId, Long holdingId);

    List<String> getDistinctCoins(Long userId);

    List<HoldingResponse> getAllHoldingsAdmin();
}