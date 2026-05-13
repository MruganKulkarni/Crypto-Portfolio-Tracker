package com.crypto.holding.controller;

import com.crypto.holding.dto.request.AddHoldingRequest;
import com.crypto.holding.dto.request.UpdateHoldingRequest;
import com.crypto.holding.dto.response.HoldingResponse;
import com.crypto.holding.service.HoldingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/holdings")
@RequiredArgsConstructor
@Slf4j
public class HoldingController {

    private final HoldingService holdingService;

    // POST /api/holdings — Add a new holding
    @PostMapping
    public ResponseEntity<HoldingResponse> addHolding(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody AddHoldingRequest request) {
        log.info("POST /api/holdings called by user: {}", userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(holdingService.addHolding(userId, request));
    }

    // GET /api/holdings — Get all holdings for current user
    @GetMapping
    public ResponseEntity<List<HoldingResponse>> getAllHoldings(
            @RequestHeader("X-User-Id") Long userId) {
        log.info("GET /api/holdings called by user: {}", userId);
        return ResponseEntity.ok(holdingService.getAllHoldings(userId));
    }

    // GET /api/holdings/{id} — Get single holding by ID
    @GetMapping("/{id}")
    public ResponseEntity<HoldingResponse> getHoldingById(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        log.info("GET /api/holdings/{} called by user: {}", id, userId);
        return ResponseEntity.ok(holdingService.getHoldingById(userId, id));
    }

    // PUT /api/holdings/{id} — Update a holding
    @PutMapping("/{id}")
    public ResponseEntity<HoldingResponse> updateHolding(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateHoldingRequest request) {
        log.info("PUT /api/holdings/{} called by user: {}", id, userId);
        return ResponseEntity.ok(holdingService.updateHolding(userId, id, request));
    }

    // DELETE /api/holdings/{id} — Delete a holding
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHolding(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        log.info("DELETE /api/holdings/{} called by user: {}", id, userId);
        holdingService.deleteHolding(userId, id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/holdings/coins — Get distinct coins
    @GetMapping("/coins")
    public ResponseEntity<List<String>> getDistinctCoins(
            @RequestHeader("X-User-Id") Long userId) {
        log.info("GET /api/holdings/coins called by user: {}", userId);
        return ResponseEntity.ok(holdingService.getDistinctCoins(userId));
    }

    // GET /api/admin/holdings — Admin: get all holdings
    @GetMapping("/admin/all")
    public ResponseEntity<List<HoldingResponse>> getAllHoldingsAdmin() {
        log.info("GET /api/admin/holdings called");
        return ResponseEntity.ok(holdingService.getAllHoldingsAdmin());
    }
}