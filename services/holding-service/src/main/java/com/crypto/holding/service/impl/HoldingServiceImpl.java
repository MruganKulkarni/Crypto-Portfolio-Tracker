package com.crypto.holding.service.impl;

import com.crypto.holding.constants.HoldingStatus;
import com.crypto.holding.dto.request.AddHoldingRequest;
import com.crypto.holding.dto.request.UpdateHoldingRequest;
import com.crypto.holding.dto.response.HoldingResponse;
import com.crypto.holding.entity.Holding;
import com.crypto.holding.exception.custom.HoldingNotFoundException;
import com.crypto.holding.exception.custom.UnauthorizedException;
import com.crypto.holding.constants.AppConstants;
import com.crypto.holding.repository.HoldingRepository;
import com.crypto.holding.service.HoldingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HoldingServiceImpl implements HoldingService {

    private final HoldingRepository holdingRepository;

    @Override
    @Transactional
    public HoldingResponse addHolding(Long userId, AddHoldingRequest request) {
        log.info("Adding holding for user: {}", userId);
        Holding holding = Holding.builder()
                .userId(userId)
                .coin(request.getCoin())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .boughtAt(request.getBoughtAt())
                .status(HoldingStatus.ACTIVE)
                .build();
        Holding saved = holdingRepository.save(holding);
        log.info("Holding added successfully with id: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    public List<HoldingResponse> getAllHoldings(Long userId) {
        log.info("Fetching all holdings for user: {}", userId);
        return holdingRepository.findByUserIdAndStatus(userId, HoldingStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HoldingResponse getHoldingById(Long userId, Long holdingId) {
        log.info("Fetching holding {} for user: {}", holdingId, userId);
        Holding holding = holdingRepository.findById(holdingId)
                .orElseThrow(() -> new HoldingNotFoundException(AppConstants.HOLDING_NOT_FOUND + holdingId));
        if (!holding.getUserId().equals(userId)) {
            throw new UnauthorizedException(AppConstants.UNAUTHORIZED);
        }
        return mapToResponse(holding);
    }

    @Override
    @Transactional
    public HoldingResponse updateHolding(Long userId, Long holdingId, UpdateHoldingRequest request) {
        log.info("Updating holding {} for user: {}", holdingId, userId);
        Holding holding = holdingRepository.findById(holdingId)
                .orElseThrow(() -> new HoldingNotFoundException(AppConstants.HOLDING_NOT_FOUND + holdingId));
        if (!holding.getUserId().equals(userId)) {
            throw new UnauthorizedException(AppConstants.UNAUTHORIZED);
        }
        holding.setQuantity(request.getQuantity());
        holding.setBuyPrice(request.getBuyPrice());
        Holding updated = holdingRepository.save(holding);
        log.info("Holding updated successfully: {}", holdingId);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteHolding(Long userId, Long holdingId) {
        log.info("Deleting holding {} for user: {}", holdingId, userId);
        Holding holding = holdingRepository.findById(holdingId)
                .orElseThrow(() -> new HoldingNotFoundException(AppConstants.HOLDING_NOT_FOUND + holdingId));
        if (!holding.getUserId().equals(userId)) {
            throw new UnauthorizedException(AppConstants.UNAUTHORIZED);
        }
        holdingRepository.delete(holding);
        log.info("Holding deleted successfully: {}", holdingId);
    }

    @Override
    public List<String> getDistinctCoins(Long userId) {
        log.info("Fetching distinct coins for user: {}", userId);
        return holdingRepository.findDistinctCoinsByUser(userId);
    }

    @Override
    public List<HoldingResponse> getAllHoldingsAdmin() {
        log.info("Admin fetching all holdings");
        return holdingRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private HoldingResponse mapToResponse(Holding holding) {
        return HoldingResponse.builder()
                .id(holding.getId())
                .userId(holding.getUserId())
                .coin(holding.getCoin())
                .quantity(holding.getQuantity())
                .buyPrice(holding.getBuyPrice())
                .status(holding.getStatus())
                .boughtAt(holding.getBoughtAt())
                .createdAt(holding.getCreatedAt())
                .updatedAt(holding.getUpdatedAt())
                .build();
    }
}