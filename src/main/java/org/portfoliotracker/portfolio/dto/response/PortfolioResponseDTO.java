package org.portfoliotracker.portfolio.dto.response;

import org.portfoliotracker.portfolio.entity.UserApp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PortfolioResponseDTO(
        BigDecimal balance,
        UserApp user,
        LocalDateTime createdAt,
        Long countAssets
) {
}
