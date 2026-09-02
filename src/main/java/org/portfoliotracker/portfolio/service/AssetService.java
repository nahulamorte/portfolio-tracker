package org.portfoliotracker.portfolio.service;

import org.portfoliotracker.portfolio.dto.response.AssetResponseDTO;
import org.portfoliotracker.portfolio.entity.Asset;
import org.portfoliotracker.portfolio.exception.PortfolioNotFoundException;
import org.portfoliotracker.portfolio.repository.AssetRepository;
import org.springframework.stereotype.Service;

@Service
public class AssetService {
    private AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    //CRUD
    //Get
    //List<>

    public AssetResponseDTO getAssetByTicker(String ticker){
        Asset asset = assetRepository.findByTicker(ticker)
                .orElseThrow(() -> new RuntimeException("No existe ningun activo con ticker: " + ticker));//To do: custome exception assetNotFound-

        AssetResponseDTO response = new AssetResponseDTO(
                asset.getName(),
                asset.getTicker(),
                asset.getAssetType()
        );
        return response;
    }

    //Post


    //Delete



}
