package org.portfoliotracker.portfolio.controller;

import org.portfoliotracker.portfolio.dto.response.PortfolioResponseDTO;
import org.portfoliotracker.portfolio.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/me")
    public ResponseEntity<PortfolioResponseDTO> getMyPortfolio(){
        PortfolioResponseDTO response = portfolioService.getMyPortfolio();
        return ResponseEntity.ok(response);
    }
}
