package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.Album;
import lombok.Getter;

@Getter
public class AlbumResponseDTO {
    
    private Long id;
    private String albumTitle;
    private Long gameId;

    public AlbumResponseDTO(Album album) {
        this.id = album.getId();
        this.albumTitle = album.getAlbumTitle();
        this.gameId = album.getGame().getId();
    }
}
