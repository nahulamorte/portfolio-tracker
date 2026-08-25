package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
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

