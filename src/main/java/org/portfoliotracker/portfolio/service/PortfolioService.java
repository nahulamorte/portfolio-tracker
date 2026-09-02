package org.portfoliotracker.portfolio.service;

import org.portfoliotracker.portfolio.exception.PortfolioNotFoundException;
import org.portfoliotracker.portfolio.repository.TransactionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.portfoliotracker.portfolio.dto.response.PortfolioResponseDTO;
import org.portfoliotracker.portfolio.entity.Portfolio;
import org.portfoliotracker.portfolio.repository.PortfolioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final TransactionRepository transactionRepository;

    public PortfolioService(PortfolioRepository portfolioRepository, TransactionRepository transactionRepository) {
        this.portfolioRepository = portfolioRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public PortfolioResponseDTO getMyPortfolio(){
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Portfolio portfolio = portfolioRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new PortfolioNotFoundException("El portfolio no existe para este usuario"));

        PortfolioResponseDTO response = new PortfolioResponseDTO(
                portfolio.getBalance(),
                portfolio.getUser(),
                portfolio.getCreatedAt(),
                transactionRepository.countDistinctAssetsByPortfolio(portfolio.getPortfolioId())
        );
        return response;
    }
}
