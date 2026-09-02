package org.portfoliotracker.portfolio.controller;

import org.portfoliotracker.portfolio.dto.response.AssetResponseDTO;
import org.portfoliotracker.portfolio.entity.Asset;
import org.portfoliotracker.portfolio.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/asset")
public class AssetController {
    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/ticker")
    public ResponseEntity<AssetResponseDTO> getAssetByTicker(@PathVariable String ticker){
        AssetResponseDTO response = assetService.getAssetByTicker(ticker);
        return ResponseEntity.ok(response);
    }


    }
}
