package org.portfoliotracker.portfolio.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.portfoliotracker.portfolio.entity.Asset;
import org.portfoliotracker.portfolio.entity.TransactionType;
import org.portfoliotracker.portfolio.entity.UserApp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long transactionId, // For frontend
        String ticker,
        @NotNull @Positive(message = "La cantidad debe ser mayor a cero")
        BigDecimal quantity,
        @NotNull @Positive(message =  "El precio debe ser mayor a cero")
        BigDecimal price,
        BigDecimal totalPrice,//QUANTITY x PRICE
        @NotNull
        TransactionType transactionType,
        LocalDateTime transactionDate
) {
}

