package io.github.manojohnsons.gamesoundtracksapi.user.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserRequestDTO {

    @Schema(description = "Username. Cannot be blank.", example = "Fulana35")
    @NotBlank(message = "O nome do usuário não pode estar em branco.")
    private String username;

    @Schema(description = "Password. Cannot be blank and must have at least 8 characters.", example = "SUPER-SEÇURE-PASSWORD!")
    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String password;
}
