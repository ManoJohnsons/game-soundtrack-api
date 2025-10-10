package io.github.manojohnsons.gamesoundtracksapi.user.dtos;

import io.github.manojohnsons.gamesoundtracksapi.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserResponseDTO {

    @Schema(description = "Unique identifier of the user.", example = "1")
    private Long id;
    @Schema(description = "Username.", example = "Fulana35")
    private String username;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
