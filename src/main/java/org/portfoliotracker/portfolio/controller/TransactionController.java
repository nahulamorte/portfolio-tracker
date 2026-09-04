package org.portfoliotracker.portfolio.controller;

import org.portfoliotracker.portfolio.dto.request.TransactionRequestDTO;
import org.portfoliotracker.portfolio.dto.response.TransactionResponseDTO;
import org.portfoliotracker.portfolio.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactionsOfUser(){
        List<TransactionResponseDTO> response = transactionService.getAllTransactionsOfUser();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO request){
        TransactionResponseDTO response = transactionService.createTransaction(request);
        return ResponseEntity.status(201).body(response);
    }
}
