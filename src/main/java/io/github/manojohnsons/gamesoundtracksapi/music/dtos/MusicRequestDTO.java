package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MusicRequestDTO {

    @NotBlank(message = "O título da música não pode estar em branco.")
    @Schema(description = "Title of the music. Cannot be blank.", example = "ANOTHER HIM")
    private String musicTitle;

    @NotNull(message = "A duração em segundos não pode ser nula.")
    @Positive(message = "A duração devem ser um número positivo.")
    @Schema(description = "Duration in seconds of the music. Cannot be null and must be a positive number.", example = "53")
    private Integer durationInSeconds;
}
