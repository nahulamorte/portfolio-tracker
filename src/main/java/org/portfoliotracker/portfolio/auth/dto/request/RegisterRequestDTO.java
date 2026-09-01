package org.portfoliotracker.portfolio.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegisterRequestDTO(
        @NotBlank
        String name,
        @Past
        LocalDate dateBirth,
        @NotBlank
        String username,
        @NotBlank @Email
        String email,
        @NotBlank @Size(min = 8)
        String password
) {
}
