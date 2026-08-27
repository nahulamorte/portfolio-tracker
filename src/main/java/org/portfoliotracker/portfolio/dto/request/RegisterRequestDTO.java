package org.portfoliotracker.portfolio.dto.request;

import java.time.LocalDate;
import java.util.Date;

public record RegisterRequestDTO(
        String name,
        LocalDate dateBirth,
        String username,
        String email,
        String password
) {
}
