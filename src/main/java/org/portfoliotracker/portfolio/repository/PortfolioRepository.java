package org.portfoliotracker.portfolio.repository;

import org.portfoliotracker.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio save(Portfolio portfolio);
}
