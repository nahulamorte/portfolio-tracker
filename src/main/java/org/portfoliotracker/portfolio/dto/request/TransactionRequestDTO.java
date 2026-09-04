package org.portfoliotracker.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.portfoliotracker.portfolio.entity.Asset;
import org.portfoliotracker.portfolio.entity.TransactionType;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotNull @Positive(message = "La cantidad debe ser mayor a cero")
        BigDecimal quantity,
        @NotNull @Positive(message =  "El precio debe ser mayor a cero")
        BigDecimal price,
        @NotNull
        TransactionType transactionType,
        @NotBlank(message = "El ticker del activo es obligatorio")
        String ticker
) {
}
