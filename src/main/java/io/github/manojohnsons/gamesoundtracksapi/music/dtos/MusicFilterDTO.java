package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MusicFilterDTO {

    @Schema(description = "Part of the name of the artist/composer to search for. Case-insensitive")
    private String composerName;
    @Schema(description = "Part of the title of the album to search for. Case-insensitive")
    private String albumTitle;
    @Schema(description = "Part of the title of the game to search for. Case-insensitive")
    private String gameTitle;
}
