package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.Composer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ComposerResponseDTO {

    @Schema(description = "Unique identifier of the artist/composer.", example = "1")
    private Long id;
    @Schema(description = "Name of the artist/composer.", example = "Toby Fox")
    private String name;

    public ComposerResponseDTO(Composer composer) {
        this.id = composer.getId();
        this.name = composer.getName();
    }
}
