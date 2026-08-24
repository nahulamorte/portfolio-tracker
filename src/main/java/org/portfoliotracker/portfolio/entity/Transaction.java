package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private BigDecimal quantity;
    private BigDecimal price;
    private Character transactionType;
    private LocalDateTime createdAt;

    @OneToOne
    private Portfolio portfolio;

    @ManyToOne
    private Asset asset;

    @ManyToOne
    private UserApp user;
}