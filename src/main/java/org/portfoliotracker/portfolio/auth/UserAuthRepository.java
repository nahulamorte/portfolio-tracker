package org.portfoliotracker.portfolio.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    UserAuth save(UserAuth userAuth);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserAuth findByUsername(String username);
}
