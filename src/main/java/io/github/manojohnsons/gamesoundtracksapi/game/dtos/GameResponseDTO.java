package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GameResponseDTO {

    @Schema(description = "Unique identifier of the game.", example = "1")
    private Long id;
    @Schema(description = "Title of the game.", example = "DELTARUNE")
    private String gameTitle;

    public GameResponseDTO(Game game) {
        this.id = game.getId();
        this.gameTitle = game.getGameTitle();
    }
}
