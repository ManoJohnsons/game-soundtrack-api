package io.github.manojohnsons.gamesoundtracksapi.music;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/games/{gameId}/albums/{albumId}/musics")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @PostMapping
    public ResponseEntity<MusicResponseDTO> addMusic(
            @PathVariable Long gameId,
            @PathVariable Long albumId,
            @RequestBody @Valid MusicRequestDTO musicRequestDTO,
            UriComponentsBuilder uriBuilder) {
        MusicResponseDTO newMusic = musicService.addMusic(gameId, albumId, musicRequestDTO);
        URI location = uriBuilder.path("/games/{gameId}/albums/{albumId}/musics/{musicId}")
                .buildAndExpand(gameId, albumId, newMusic.getId())
                .toUri();

        return ResponseEntity.created(location).body(newMusic);
    }

    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> listMusics(
            @PathVariable Long gameId,
            @PathVariable Long albumId) {
        List<MusicResponseDTO> allMusics = musicService.listMusicsFromAlbum(gameId, albumId);

        return ResponseEntity.ok(allMusics);
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<MusicResponseDTO> findMusicById(
            @PathVariable Long gameId,
            @PathVariable Long albumId,
            @PathVariable Long musicId) {
        MusicResponseDTO musicFetched = musicService.findMusicById(gameId, albumId, musicId);

        return ResponseEntity.ok(musicFetched);
    }

    @PutMapping("/{musicId}")
    public ResponseEntity<MusicResponseDTO> updateMusicById(
            @PathVariable Long gameId,
            @PathVariable Long albumId,
            @PathVariable Long musicId,
            @RequestBody @Valid MusicRequestDTO musicRequestDTO) {
        MusicResponseDTO musicUpdate = musicService.updateMusic(gameId, albumId, musicId, musicRequestDTO);

        return ResponseEntity.ok(musicUpdate);
    }

    @DeleteMapping("/{musicId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMusicById(
            @PathVariable Long gameId,
            @PathVariable Long albumId,
            @PathVariable Long musicId) {
        musicService.deleteMusic(gameId, albumId, musicId);
    }
}
