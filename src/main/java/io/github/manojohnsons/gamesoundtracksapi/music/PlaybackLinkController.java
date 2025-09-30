package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.PlaybackLinkRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.PlaybackLinkResponseDTO;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("musics/{musicId}/links")
public class PlaybackLinkController {

    private final PlaybackLinkService playbackLinkService;

    public PlaybackLinkController(PlaybackLinkService playbackLinkService) {
        this.playbackLinkService = playbackLinkService;
    }

    @PostMapping
    public ResponseEntity<PlaybackLinkResponseDTO> addLink(@PathVariable Long musicId,
            @RequestBody @Valid PlaybackLinkRequestDTO dto, UriComponentsBuilder uriBuilder) {
        PlaybackLinkResponseDTO newLink = playbackLinkService.addLink(musicId, dto);
        URI location = uriBuilder.path("musics/{musicId}/links/{linkId}")
                .buildAndExpand(musicId, newLink.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLink);
    }

    @GetMapping
    public ResponseEntity<List<PlaybackLinkResponseDTO>> listLinks(@PathVariable Long musicId) {
        List<PlaybackLinkResponseDTO> allLinks = playbackLinkService.listAllLinksOfAMusic(musicId);

        return ResponseEntity.ok(allLinks);
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<PlaybackLinkResponseDTO> getOneLink(@PathVariable Long musicId, @PathVariable Long linkId) {
        PlaybackLinkResponseDTO link = playbackLinkService.getOneLinkOfAMusic(musicId, linkId);

        return ResponseEntity.ok(link);
    }

    @PutMapping("/{linkId}")
    public ResponseEntity<PlaybackLinkResponseDTO> updateLink(@PathVariable Long musicId, @PathVariable Long linkId, @RequestBody @Valid PlaybackLinkRequestDTO dto) {
        PlaybackLinkResponseDTO linkUpdate = playbackLinkService.updateLinkOfAMusic(musicId, linkId, dto);

        return ResponseEntity.ok(linkUpdate);
    }

    @DeleteMapping("/{linkId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLink(@PathVariable Long musicId, @PathVariable Long linkId) {
        playbackLinkService.deleteLink(musicId, linkId);
    }
}
