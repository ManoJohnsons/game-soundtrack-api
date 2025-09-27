package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.Music;
import lombok.Getter;

@Getter
public class MusicResponseDTO {
    
    private Long id;
    private String musicTitle;
    private Integer durationInSeconds;
    private Long albumId;

    public MusicResponseDTO(Music music) {
        this.id = music.getId();
        this.musicTitle = music.getMusicTitle();
        this.durationInSeconds = music.getDurationInSeconds();
        this.albumId = music.getAlbum().getId();
    }
}
