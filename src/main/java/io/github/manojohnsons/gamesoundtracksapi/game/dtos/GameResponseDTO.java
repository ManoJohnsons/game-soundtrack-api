package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import lombok.Getter;

@Getter
public class GameResponseDTO {

    private Long id;
    private String gameTitle;

    public GameResponseDTO(Game game) {
        this.id = game.getId();
        this.gameTitle = game.getGameTitle();
    }
}
