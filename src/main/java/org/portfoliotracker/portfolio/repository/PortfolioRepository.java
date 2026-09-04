package org.portfoliotracker.portfolio.repository;

import org.portfoliotracker.portfolio.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Port;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio save(Portfolio portfolio);

    @Query("""
    SELECT p
    FROM Portfolio p
    WHERE p.user.userId = (
        SELECT ua.idUser FROM UserAuth ua WHERE ua.username = :username
    )
""")
    Optional<Portfolio> findByUsername(@Param("username") String username);



}
