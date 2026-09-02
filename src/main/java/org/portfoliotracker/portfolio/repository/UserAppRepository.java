package org.portfoliotracker.portfolio.repository;

import org.portfoliotracker.portfolio.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, Long> {
    UserApp save(UserApp userApp);
}
