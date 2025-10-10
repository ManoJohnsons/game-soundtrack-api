package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AlbumRequestDTO {

    @NotBlank(message = "O título do album não pode estar em branco.")
    @Schema(description = "Title of the album. Cannot be blank.", example = "DELTARUNE Chapter 1 Soundtrack - Official")
    private String albumTitle;
}
