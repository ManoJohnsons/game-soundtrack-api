package io.github.manojohnsons.gamesoundtracksapi.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
		@Schema(description = "Username defined in the user route.", example = "Fulana35") @NotBlank String login,
		@Schema(description = "Password defined in the user route.", example = "SUPER-SEÇURE-PASSWORD!") @NotBlank String password) {
}
