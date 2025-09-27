package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MusicRequestDTO {
    
    @NotBlank(message = "O título da música não pode estar em branco.")
    private String musicTitle;

    @NotNull(message = "A duração em segundos não pode ser nula.")
    @Positive(message = "A duração devem ser um número positivo.")
    private Integer durationInSeconds;
}
