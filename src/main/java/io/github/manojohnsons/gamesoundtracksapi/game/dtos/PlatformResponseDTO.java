package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import io.github.manojohnsons.gamesoundtracksapi.game.Platform;
import lombok.Getter;

@Getter
public class PlatformResponseDTO {

    private Long id;
    private String platformName;

    public PlatformResponseDTO(Platform platform) {
        this.id = platform.getId();
        this.platformName = platform.getPlatformName();
    }
}
