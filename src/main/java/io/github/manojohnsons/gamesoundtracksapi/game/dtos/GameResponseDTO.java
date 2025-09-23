package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import lombok.Getter;
import lombok.Setter;

public class GameResponseDTO {
    
    @Getter @Setter private Long id;
    @Getter @Setter private String gameTitle;

    public GameResponseDTO(Game game) {
        this.id = game.getId();
        this.gameTitle = game.getGameTitle();
    }
}
