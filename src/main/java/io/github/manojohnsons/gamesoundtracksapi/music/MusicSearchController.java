package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicFilterDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicResponseDTO;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/musics")
public class MusicSearchController {
    
    private final MusicService musicService;

    public MusicSearchController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> search(MusicFilterDTO filterDTO) {
        List<MusicResponseDTO> result = musicService.search(filterDTO);
        return ResponseEntity.ok(result);
    }
    
}
