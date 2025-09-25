package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ComposerRequestDTO {

    @NotBlank(message = "O nome do artista não pode estar em branco.")
    private String name;
}
