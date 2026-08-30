package org.portfoliotracker.portfolio.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    UserAuth save(UserAuth userAuth);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserAuth> findByUsername(String username);
}
