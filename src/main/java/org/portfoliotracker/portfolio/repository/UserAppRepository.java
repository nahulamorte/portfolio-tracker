package org.portfoliotracker.portfolio.repository;

import org.portfoliotracker.portfolio.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    UserApp save(UserApp userApp);
}
