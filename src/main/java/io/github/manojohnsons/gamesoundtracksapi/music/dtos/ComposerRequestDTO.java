package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ComposerRequestDTO {

    @NotBlank(message = "O nome do artista não pode estar em branco.")
    @Schema(description = "Name of the artist/composer. Cannot be blank.", example = "Toby Fox")
    private String name;
}
