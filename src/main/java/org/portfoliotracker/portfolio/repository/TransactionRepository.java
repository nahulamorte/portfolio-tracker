package org.portfoliotracker.portfolio.repository;

import org.portfoliotracker.portfolio.entity.Portfolio;
import org.portfoliotracker.portfolio.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long> {

    @Query("SELECT COUNT(DISTINCT t.asset.assetId) FROM Transaction t WHERE t.portfolio.portfolioId = :portfolioId")
    Long countDistinctAssetsByPortfolio(@Param("portfolioId") Long portfolioId);

    List<Transaction> findByPortfolio_PortfolioIdOrderByCreatedAtDesc(Long portfolioId);

    @Query("""
    SELECT
        COALESCE(SUM(CASE WHEN t.transactionType = 'BUY' THEN t.quantity ELSE 0 END), 0)
        -
        COALESCE(SUM(CASE WHEN t.transactionType = 'SELL' THEN t.quantity ELSE 0 END), 0)
    FROM Transaction t
    WHERE t.asset.assetId = :assetId
      AND t.portfolio.portfolioId = :portfolioId
""")
    BigDecimal getNetQuantity(@Param("assetId") Long assetId, @Param("portfolioId") Long portfolioId);

    List<Transaction> findAllByPortfolio(Portfolio p);
}
