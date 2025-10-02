package io.github.manojohnsons.gamesoundtracksapi.game;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameFilterDTO;

@Component
public class GameSpecificationBuilder {
    
    public Specification<Game> build(GameFilterDTO filter) {
        Specification<Game> spec = Specification.unrestricted();

        if (filter.getGameTitle() != null && !filter.getGameTitle().isBlank()) {
            spec = spec.and(GameSpecifications.withTitle(filter.getGameTitle()));
        }

        return spec;
    }
}
