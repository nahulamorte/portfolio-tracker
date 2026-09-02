package org.portfoliotracker.portfolio.auth;

import jakarta.persistence.*;
import lombok.*;
import org.portfoliotracker.portfolio.entity.UserApp;

import java.time.LocalDateTime;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserAuth {
    @Id
    @Column(name = "id_user")
    private Long idUser;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user")
    private UserApp userApp;

    private String username;
    private String email;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
