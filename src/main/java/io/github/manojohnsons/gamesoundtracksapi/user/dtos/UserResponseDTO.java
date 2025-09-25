package io.github.manojohnsons.gamesoundtracksapi.user.dtos;

import io.github.manojohnsons.gamesoundtracksapi.user.User;
import lombok.Getter;

@Getter
public class UserResponseDTO {
    
    private Long id;
    private String username;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
