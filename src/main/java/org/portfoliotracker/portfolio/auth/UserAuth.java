package org.portfoliotracker.portfolio.auth;

import jakarta.persistence.*;
import lombok.*;
import org.portfoliotracker.portfolio.entity.UserApp;

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
