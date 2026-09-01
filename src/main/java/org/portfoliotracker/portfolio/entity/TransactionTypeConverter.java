package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

    @Override
    public String convertToDatabaseColumn(TransactionType type) {
        if (type == null) return null;
        return switch (type) {
            case BUY -> "B";
            case SELL -> "S";
        };
    }

    @Override
    public TransactionType convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;
        return switch (dbValue) {
            case "B" -> TransactionType.BUY;
            case "S" -> TransactionType.SELL;
            default -> throw new IllegalArgumentException("Valor de transaction_type desconocido: " + dbValue);
        };
    }
}