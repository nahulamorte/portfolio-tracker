package org.portfoliotracker.portfolio.service;

import org.portfoliotracker.portfolio.dto.response.AssetResponseDTO;
import org.portfoliotracker.portfolio.entity.Asset;
import org.portfoliotracker.portfolio.entity.AssetType;
import org.portfoliotracker.portfolio.exception.AssetNotFoundException;
import org.portfoliotracker.portfolio.exception.AssetTypeRequiredException;
import org.portfoliotracker.portfolio.exception.PortfolioNotFoundException;
import org.portfoliotracker.portfolio.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<AssetResponseDTO> getAllAssets(){
        List<AssetResponseDTO> responses = new ArrayList<>();
        List<Asset> assets = assetRepository.findAll();
        for (Asset asset : assets){
            AssetResponseDTO response = new AssetResponseDTO(
                    asset.getName(),
                    asset.getTicker(),
                    asset.getAssetType()
            );
            responses.add(response);
        }
        return responses;
    }


    public Asset findOrCreate(String ticker, AssetType assetType) {
        return assetRepository.findByTicker(ticker)
                .orElseGet(() -> {
                    if (assetType == null) {
                        throw new AssetTypeRequiredException(
                                "El ticker '" + ticker + "' no existe. Especificá si es STOCK o CRYPTO para crearlo.");
                    }
                    Asset newAsset = Asset.builder()
                            .ticker(ticker)
                            .name(ticker)
                            .assetType(assetType)
                            .build();
                    return assetRepository.save(newAsset);
                });
    }


}
