package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import io.github.manojohnsons.gamesoundtracksapi.game.Platform;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlatformRequestDTO {

    @NotBlank(message = "O nome da plataforma não deve estar em branco.")
    @Getter
    private String platformName;

    public PlatformRequestDTO(Platform platform) {
        this.platformName = platform.getPlatformName();
    }
}
