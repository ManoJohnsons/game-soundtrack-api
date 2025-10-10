package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import java.time.LocalDate;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GameRequestDTO {

    @NotBlank(message = "O nome do jogo não pode estar em branco.")
    @Schema(description = "Title of the game. Cannot be blank.", example = "DELTARUNE")
    private String gameTitle;

    @NotBlank(message = "O nome da desenvolvedora não pode estar em branco.")
    @Schema(description = "Developer of the game. Cannot be blank.", example = "tobyfox")
    private String developer;

    @NotBlank(message = "O nome da publicadora não pode estar em branco.")
    @Schema(description = "Publisher of the game. Cannot be blank.", example = "tobyfox")
    private String publisher;

    @NotNull(message = "O ano de lançamento não pode ser nulo.")
    @PastOrPresent(message = "O ano de lançamento não pode ser no futuro.")
    @Schema(description = "Release date of the game. Cannot be null and be in the future.", example = "2025-06-04")
    private LocalDate releaseYear;

    public GameRequestDTO(Game game) {
        this.gameTitle = game.getGameTitle();
        this.developer = game.getDeveloper();
        this.publisher = game.getPublisher();
        this.releaseYear = game.getReleaseYear();
    }
}
