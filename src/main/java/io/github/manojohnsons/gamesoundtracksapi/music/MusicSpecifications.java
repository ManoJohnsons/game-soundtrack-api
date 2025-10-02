package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.data.jpa.domain.Specification;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import jakarta.persistence.criteria.Join;

public class MusicSpecifications {

    public static Specification<Music> fromComposer(String composerName) {
        return (root, query, builder) -> {
            Join<Music, Composer> composerJoin = root.join("composers");
            return builder.like(builder.lower(composerJoin.get("name")), "%" + composerName.toLowerCase() + "%");
        };
    }

    public static Specification<Music> fromAlbum(String albumTitle) {
        return (root, query, builder) -> {
            Join<Music, Album> albumJoin = root.join("album");
            return builder.like(builder.lower(albumJoin.get("albumTitle")), "%" + albumTitle.toLowerCase() + "%");
        };
    }

    public static Specification<Music> fromGame(String gameTitle) {
        return (root, query, builder) -> {
            Join<Music, Album> albumJoin = root.join("album");
            Join<Album, Game> gameJoin = albumJoin.join("game");
            return builder.like(builder.lower(gameJoin.get("gameTitle")), "%" + gameTitle + "%");
        };
    }
}
