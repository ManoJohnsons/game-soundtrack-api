package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import io.github.manojohnsons.gamesoundtracksapi.game.Platform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PlatformResponseDTO {

    @Schema(description = "Unique identifier of the platform.", example = "1")
    private Long id;
    @Schema(description = "Name of the platform.", example = "Steam")
    private String platformName;

    public PlatformResponseDTO(Platform platform) {
        this.id = platform.getId();
        this.platformName = platform.getPlatformName();
    }
}
