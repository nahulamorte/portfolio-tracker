package org.portfoliotracker.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
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
