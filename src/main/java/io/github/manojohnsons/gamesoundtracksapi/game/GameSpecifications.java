package io.github.manojohnsons.gamesoundtracksapi.game;

import org.springframework.data.jpa.domain.Specification;

public class GameSpecifications {

    public static Specification<Game> withTitle(String gameTitle) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("gameTitle")),
                "%" + gameTitle.toLowerCase() + "%");
    }
}
