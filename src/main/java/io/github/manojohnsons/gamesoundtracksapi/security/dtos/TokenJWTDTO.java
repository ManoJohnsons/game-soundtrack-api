package io.github.manojohnsons.gamesoundtracksapi.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenJWTDTO(
		@Schema(description = "JWT for authenticated users.", example = "<JWT_TOKEN>") String token) {
}
