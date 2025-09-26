package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumResponseDTO;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;

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

    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> listAlbuns(@PathVariable Long gameId) {
        List<AlbumResponseDTO> albuns = albumService.listAlbunsOfAGame(gameId);
        return ResponseEntity.ok(albuns);
    }
    
    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumResponseDTO> searchAlbumById(@PathVariable Long gameId, @PathVariable Long albumId) {
        AlbumResponseDTO album = albumService.searchAlbumById(gameId, albumId);
        return ResponseEntity.ok(album);
    }
}
