package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.Composer;
import lombok.Getter;

@Getter
public class ComposerResponseDTO {

    private Long id;
    private String name;

    public ComposerResponseDTO(Composer composer) {
        this.id = composer.getId();
        this.name = composer.getName();
    }
}
