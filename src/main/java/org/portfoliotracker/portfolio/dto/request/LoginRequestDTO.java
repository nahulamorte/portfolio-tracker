package org.portfoliotracker.portfolio.dto.request;
import jakarta.validation.constraints.*;

public record LoginRequestDTO(
        @NotNull
        String username,
        @NotNull
        @NotBlank
        @Size(min = 8)
        String password
) {
}
