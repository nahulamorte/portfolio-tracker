package org.portfoliotracker.portfolio.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    UserAuth save(UserAuth userAuth);

    boolean existsByUsername(String username);

    UserAuth findByUsername(String username);
}
