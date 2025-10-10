package io.github.manojohnsons.gamesoundtracksapi.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    boolean existsByPlatformName(String platformName);
}
