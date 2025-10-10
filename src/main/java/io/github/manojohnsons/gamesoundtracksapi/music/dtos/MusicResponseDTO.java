package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.Music;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MusicResponseDTO {

    @Schema(description = "Unique identifier of the music.", example = "1")
    private Long id;
    @Schema(description = "Title of the music.", example = "ANOTHER HIM")
    private String musicTitle;
    @Schema(description = "Duration in seconds of the music.", example = "53")
    private Integer durationInSeconds;
    @Schema(description = "Unique identifier of the album associated with this music.", example = "1")
    private Long albumId;

    public MusicResponseDTO(Music music) {
        this.id = music.getId();
        this.musicTitle = music.getMusicTitle();
        this.durationInSeconds = music.getDurationInSeconds();
        this.albumId = music.getAlbum().getId();
    }
}
