package com.crypto.price_service.service;

import com.crypto.price_service.client.CoinGeckoClient;
import com.crypto.price_service.entity.CryptoPrice;
import com.crypto.price_service.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PriceService {

    private final PriceRepository repository;

    private final CoinGeckoClient coinGeckoClient;

    public PriceService(
            PriceRepository repository,
            CoinGeckoClient coinGeckoClient
    ) {
        this.repository = repository;
        this.coinGeckoClient = coinGeckoClient;
    }

    public List<CryptoPrice> fetchAndSavePrices() {

        Map<String, Map<String, Object>> response =
                coinGeckoClient.getPrices(
                        "bitcoin,ethereum",
                        "usd",
                        true
                );

        List<CryptoPrice> savedPrices = new ArrayList<>();

        for (String coin : response.keySet()) {

            Map<String, Object> coinData =
                    response.get(coin);

            CryptoPrice cryptoPrice = new CryptoPrice();

            cryptoPrice.setCoinId(coin);

            cryptoPrice.setSymbol(
                    coin.substring(0, 3).toUpperCase()
            );

            cryptoPrice.setName(coin);

            cryptoPrice.setCurrentPrice(
                    ((Number) coinData.get("usd"))
                            .doubleValue()
            );

            cryptoPrice.setMarketCap(
                    coinData.get("usd_market_cap") != null
                            ? ((Number) coinData.get("usd_market_cap"))
                            .doubleValue()
                            : 0.0
            );

            cryptoPrice.setLastUpdated(
                    LocalDateTime.now()
            );

            savedPrices.add(
                    repository.save(cryptoPrice)
            );
        }

        return savedPrices;
    }

    public List<CryptoPrice> getAllPrices() {
        return repository.findAll();
    }

    public CryptoPrice getPriceByCoin(
            String coinId
    ) {

        return repository.findByCoinId(coinId)
                .orElseThrow(
                        () -> new RuntimeException("Coin not found")
                );
    }

    public List<CryptoPrice> getCoinHistory(
            String coinId
    ) {

        return repository
                .findByCoinIdOrderByLastUpdatedDesc(
                        coinId
                );
    }
}