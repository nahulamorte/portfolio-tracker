package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long transactionId;

    private BigDecimal quantity;
    private BigDecimal price;

    @Column(name = "transaction_type", columnDefinition = "CHAR(1)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private TransactionType transactionType; //BUY, SELL

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_portfolio", nullable = false)
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "id_asset", nullable = false)
    private Asset asset;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}