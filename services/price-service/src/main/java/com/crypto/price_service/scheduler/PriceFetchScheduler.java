package com.crypto.price_service.scheduler;

import com.crypto.price_service.service.PriceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceFetchScheduler {

    private final PriceService priceService;

    public PriceFetchScheduler(
            PriceService priceService
    ) {
        this.priceService = priceService;
    }

    @Scheduled(fixedRate = 300000)
    public void fetchPrices() {

        System.out.println(
                "Fetching latest crypto prices..."
        );

        priceService.fetchAndSavePrices();

        System.out.println(
                "Prices updated successfully"
        );
    }
}