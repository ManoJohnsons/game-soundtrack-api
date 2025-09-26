package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumResponseDTO;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("games/{gameId}/albuns")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> addAlbum(@PathVariable Long gameId,
            @RequestBody @Valid AlbumRequestDTO albumRequestDTO, UriComponentsBuilder uriBuilder) {
        AlbumResponseDTO newAlbum = albumService.addAlbum(gameId, albumRequestDTO);
        URI location = uriBuilder.path("games/{gameId}/albuns/{albumId}").buildAndExpand(gameId, newAlbum.getId())
                .toUri();

        return ResponseEntity.created(location).body(newAlbum);
    }

}
