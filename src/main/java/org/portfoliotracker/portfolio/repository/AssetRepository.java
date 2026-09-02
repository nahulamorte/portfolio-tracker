package org.portfoliotracker.portfolio.repository;

import org.portfoliotracker.portfolio.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    boolean existsByTicker(String ticker);

    Optional<Asset> findByTicker(String ticker);

    Optional<List<Asset>> findAllByTicker(String ticker);

    Asset save(Asset asset);

    void delete(Asset entity);

    void deleteById(Long assetId);
}
