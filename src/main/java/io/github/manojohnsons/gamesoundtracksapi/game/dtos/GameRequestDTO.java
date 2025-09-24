package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import java.util.Date;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameRequestDTO {

    @NotBlank(message = "O nome do jogo não pode estar em branco.")
    private String gameTitle;

    @NotBlank(message = "O nome da desenvolvedora não pode estar em branco.")
    private String developer;

    @NotBlank(message = "O nome da publicadora não pode estar em branco.")
    private String publisher;

    @NotNull(message = "O ano de lançamento não pode ser nulo.")
    @PastOrPresent(message = "O ano de lançamento não pode ser no futuro.")
    private Date releaseYear;

    public GameRequestDTO(Game game) {
        this.gameTitle = game.getGameTitle();
        this.developer = game.getDeveloper();
        this.publisher = game.getPublisher();
        this.releaseYear = game.getReleaseYear();
    }
}
