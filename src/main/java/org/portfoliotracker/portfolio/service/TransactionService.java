package org.portfoliotracker.portfolio.service;

import org.portfoliotracker.portfolio.dto.request.TransactionRequestDTO;
import org.portfoliotracker.portfolio.dto.response.TransactionResponseDTO;
import org.portfoliotracker.portfolio.entity.Asset;
import org.portfoliotracker.portfolio.entity.Portfolio;
import org.portfoliotracker.portfolio.entity.Transaction;
import org.portfoliotracker.portfolio.entity.TransactionType;
import org.portfoliotracker.portfolio.exception.AssetNotFoundException;
import org.portfoliotracker.portfolio.exception.InsufficientAssetQuantityException;
import org.portfoliotracker.portfolio.exception.PortfolioNotFoundException;
import org.portfoliotracker.portfolio.repository.AssetRepository;
import org.portfoliotracker.portfolio.repository.PortfolioRepository;
import org.portfoliotracker.portfolio.repository.TransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AssetRepository assetRepository;
    private final PortfolioRepository portfolioRepository;

    public TransactionService(TransactionRepository transactionRepository, AssetRepository assetRepository, PortfolioRepository portfolioRepository) {
        this.transactionRepository = transactionRepository;
        this.assetRepository = assetRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public TransactionResponseDTO createTransaction(TransactionRequestDTO request){
        String ticker = request.ticker();
        Asset asset = assetRepository.findByTicker(ticker)
                .orElseGet(() -> {
                    Asset newAsset = Asset.builder()
                            .ticker(ticker)
                            .name(ticker) // placeholder until Epic 3, when we validate against the external API
                            .assetType("UNKNOWN") //Same as above, or ask for it in the request
                            .build();
                    return assetRepository.save(newAsset);
                });

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Portfolio portfolio = portfolioRepository.findByUsername(currentUsername)
                .orElseThrow(()-> new PortfolioNotFoundException("El usuario no tiene un portfolio"));


        if (request.transactionType() == TransactionType.SELL){
            BigDecimal netQuantity = transactionRepository.getNetQuantity(asset.getAssetId(), portfolio.getPortfolioId());
            if (request.quantity().compareTo(netQuantity) > 0) {
                throw new InsufficientAssetQuantityException("No tenés suficiente cantidad para vender");
            }
        }

        Transaction newTransaction = Transaction.builder()
                .quantity(request.quantity())
                .price(request.price())
                .transactionType(request.transactionType())
                .portfolio(portfolio)
                .asset(asset)
                .build();

        Transaction saved = transactionRepository.save(newTransaction);

        return new TransactionResponseDTO(
                saved.getTransactionId(),
                asset.getTicker(),
                saved.getQuantity(),
                saved.getPrice(),
                saved.getQuantity().multiply(saved.getPrice()),
                saved.getTransactionType(),
                saved.getCreatedAt()
        );
    }

    public List<TransactionResponseDTO> getAllTransactionsOfUser(){
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Portfolio portfolio = portfolioRepository.findByUsername(currentUsername)
                .orElseThrow(()-> new PortfolioNotFoundException("Este usuario no contiene portfolio"));

        List<TransactionResponseDTO> responses = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findAllByPortfolio(portfolio);
        for (Transaction transaction : transactions){
            TransactionResponseDTO response = new TransactionResponseDTO(
                    transaction.getTransactionId(),
                    transaction.getAsset().getTicker(),
                    transaction.getQuantity(),
                    transaction.getPrice(),
                    transaction.getQuantity().multiply(transaction.getPrice()),
                    transaction.getTransactionType(),
                    transaction.getCreatedAt()
            );
            responses.add(response);
        }
        return responses;
    }
}
