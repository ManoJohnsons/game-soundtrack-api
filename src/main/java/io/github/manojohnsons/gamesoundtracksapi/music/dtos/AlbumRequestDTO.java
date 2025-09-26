package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AlbumRequestDTO {
    
    @NotBlank(message = "O título do album não pode estar em branco.")
    private String albumTitle;
}
