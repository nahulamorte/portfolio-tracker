package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "id_user", unique = true, nullable = false)
    private UserApp user;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
