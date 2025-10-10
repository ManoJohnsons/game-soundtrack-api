package io.github.manojohnsons.gamesoundtracksapi.music.dtos;

import io.github.manojohnsons.gamesoundtracksapi.music.Album;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AlbumResponseDTO {

    @Schema(description = "Unique identifier of the album.", example = "1")
    private Long id;
    @Schema(description = "Title of the album.", example = "DELTARUNE Chapter 1 Soundtrack - Official")
    private String albumTitle;
    @Schema(description = "Unique identifier of the game associated with this album.", example = "1")
    private Long gameId;

    public AlbumResponseDTO(Album album) {
        this.id = album.getId();
        this.albumTitle = album.getAlbumTitle();
        this.gameId = album.getGame().getId();
    }
}
