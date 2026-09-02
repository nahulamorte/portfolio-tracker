package org.portfoliotracker.portfolio.dto.response;

public record AssetResponseDTO(
        String name,
        String ticker,
        String assetType
) {
}
