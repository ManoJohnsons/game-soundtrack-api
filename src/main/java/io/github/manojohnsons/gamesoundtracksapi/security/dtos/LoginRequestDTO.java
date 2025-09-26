package io.github.manojohnsons.gamesoundtracksapi.security.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String login, @NotBlank String password) {

}
