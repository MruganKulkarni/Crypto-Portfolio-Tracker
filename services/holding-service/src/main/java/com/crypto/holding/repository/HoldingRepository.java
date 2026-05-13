package com.crypto.holding.repository;

import com.crypto.holding.constants.HoldingStatus;
import com.crypto.holding.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    List<Holding> findByUserIdAndStatus(Long userId, HoldingStatus status);

    List<Holding> findByUserId(Long userId);

    @Query("SELECT SUM(h.buyPrice * h.quantity) FROM Holding h " +
            "WHERE h.userId = :userId AND h.status = 'ACTIVE'")
    BigDecimal getTotalInvestedByUser(@Param("userId") Long userId);

    @Query("SELECT DISTINCT h.coin FROM Holding h WHERE h.userId = :userId")
    List<String> findDistinctCoinsByUser(@Param("userId") Long userId);
}