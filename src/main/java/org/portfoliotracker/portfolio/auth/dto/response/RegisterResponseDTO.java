package org.portfoliotracker.portfolio.auth.dto.response;

public record RegisterResponseDTO(
        String username,
        String token
) {
}
