package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor @Builder
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;

    @Column(name = "date_birth")
    private LocalDate dateBirth;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
