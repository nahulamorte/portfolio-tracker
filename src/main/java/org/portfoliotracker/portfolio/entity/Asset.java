package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Asset {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long assetId;
    private String name;
    private String ticker;
    @Column(name = "asset_type")
    private String assetType;
    private String source;
    @Column(name = "created_at")

    private LocalDateTime createdAt;
}

