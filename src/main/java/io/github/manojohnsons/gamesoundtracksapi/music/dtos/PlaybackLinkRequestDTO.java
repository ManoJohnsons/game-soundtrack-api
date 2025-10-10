package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import org.hibernate.validator.constraints.URL;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlaybackLinkRequestDTO {

    @NotBlank(message = "O nome da plataforma não pode estar em branco.")
    @Schema(description = "Name of the platform player. Cannot be blank.", example = "Youtube")
    private String platformName;

    @NotBlank
    @URL(message = "A URL fornecida é inválida.")
    @Schema(description = "Playback link of the music. The URL of the playback music must be valid.", example = "https://www.youtube.com/watch?v=XEdoMoV4D6k")
    private String musicUrl;
}
