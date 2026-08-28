package org.portfoliotracker.portfolio.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
}
