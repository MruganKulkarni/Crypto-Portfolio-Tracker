package com.crypto.price_service.controller;

import com.crypto.price_service.entity.CryptoPrice;
import com.crypto.price_service.service.PriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final PriceService priceService;

    public PriceController(
            PriceService priceService
    ) {
        this.priceService = priceService;
    }

    // 1. GET /api/prices

    @GetMapping("/prices")
    public List<CryptoPrice> getAllPrices() {

        return priceService.getAllPrices();
    }

    // 2. GET /api/prices/{coin}

    @GetMapping("/prices/{coin}")
    public CryptoPrice getPriceByCoin(
            @PathVariable String coin
    ) {

        return priceService.getPriceByCoin(coin);
    }

    // 3. GET /api/prices/{coin}/history

    @GetMapping("/prices/{coin}/history")
    public List<CryptoPrice> getCoinHistory(
            @PathVariable String coin
    ) {

        return priceService.getCoinHistory(coin);
    }

    // 4. POST /api/admin/prices/refresh

    @PostMapping("/admin/prices/refresh")
    public List<CryptoPrice> refreshPrices() {

        return priceService.fetchAndSavePrices();
    }

    // 5. DELETE /api/admin/prices/cache

    @DeleteMapping("/admin/prices/cache")
    public String clearCache() {

        return "Cache cleared successfully";
    }
}