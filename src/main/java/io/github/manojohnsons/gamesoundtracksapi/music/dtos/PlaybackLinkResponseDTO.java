package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.PlaybackLink;
import lombok.Getter;

@Getter
public class PlaybackLinkResponseDTO {
    
    private Long id;
    private String platformName;
    private String musicUrl;

    public PlaybackLinkResponseDTO(PlaybackLink link) {
        this.id = link.getId();
        this.platformName = link.getPlatformName();
        this.musicUrl = link.getMusicUrl();
    }
}
