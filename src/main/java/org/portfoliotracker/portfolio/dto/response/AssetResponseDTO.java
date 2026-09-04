package org.portfoliotracker.portfolio.dto.response;

import org.portfoliotracker.portfolio.entity.AssetType;

public record AssetResponseDTO(
        String name,
        String ticker,
        AssetType assetType){
}

