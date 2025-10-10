package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.PlaybackLink;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PlaybackLinkResponseDTO {

    @Schema(description = "Unique identifier of the playback link.", example = "1")
    private Long id;
    @Schema(description = "Name of the platform player.", example = "Youtube")
    private String platformName;
    @Schema(description = "Playback link of the music.", example = "https://www.youtube.com/watch?v=XEdoMoV4D6k")
    private String musicUrl;

    public PlaybackLinkResponseDTO(PlaybackLink link) {
        this.id = link.getId();
        this.platformName = link.getPlatformName();
        this.musicUrl = link.getMusicUrl();
    }
}
