package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GameFilterDTO {

    @Schema(description = "Part of the title of the game to search for. Case-insensitive")
    private String gameTitle;

}