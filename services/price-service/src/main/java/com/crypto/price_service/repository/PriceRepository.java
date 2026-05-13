package com.crypto.price_service.repository;

import com.crypto.price_service.entity.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceRepository
        extends JpaRepository<CryptoPrice, Long> {

    Optional<CryptoPrice> findByCoinId(String coinId);


    List<CryptoPrice> findByCoinIdOrderByLastUpdatedDesc(
            String coinId
    );
}

