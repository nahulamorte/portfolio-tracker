package org.portfoliotracker.portfolio.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginResponseDTO(
        String username,
        String token
) {
}
