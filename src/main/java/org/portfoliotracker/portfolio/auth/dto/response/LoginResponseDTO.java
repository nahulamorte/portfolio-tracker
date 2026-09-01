package org.portfoliotracker.portfolio.auth.dto.response;

public record LoginResponseDTO(
        String username,
        String token
) {
}
