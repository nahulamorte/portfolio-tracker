package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserAuth {
    @Id
    @OneToOne
    @JoinColumn(name = "id_user")
    private UserApp userApp;
    private String username;
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
}
