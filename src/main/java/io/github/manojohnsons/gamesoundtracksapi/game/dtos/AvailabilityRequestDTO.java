package io.github.manojohnsons.gamesoundtracksapi.game.dtos;

import org.hibernate.validator.constraints.URL;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AvailabilityRequestDTO {

    @NotBlank
    @URL(message = "A URL de compra fornecida é inválida.")
    @Schema(description = "Purchase link of the game. The URL of the game must be valid.", example = "https://store.steampowered.com/app/1671210/DELTARUNE/")
    private String purchaseUrl;
}
