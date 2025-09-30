package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PlaybackLinkRequestDTO {
    
    @NotBlank(message = "O nome da plataforma não pode estar em branco.")
    private String platformName;

    @NotBlank
    @URL(message = "A URL fornecida é inválida.")
    private String musicUrl;
}
