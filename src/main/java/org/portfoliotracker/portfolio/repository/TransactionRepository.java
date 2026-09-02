package org.portfoliotracker.portfolio.repository;

import org.portfoliotracker.portfolio.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction, Long> {

    @Query("SELECT COUNT(DISTINCT t.asset.assetId) FROM Transaction t WHERE t.portfolio.portfolioId = :portfolioId")
    Long countDistinctAssetsByPortfolio(@Param("portfolioId") Long portfolioId);
}
