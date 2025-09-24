package io.github.manojohnsons.gamesoundtracksapi.music;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/composers")
public class ComposerController {

    @Autowired
    private ComposerService service;

    @PostMapping
    public ResponseEntity<ComposerResponseDTO> insertComposer(@RequestBody @Valid ComposerRequestDTO composerRequestDTO,
            UriComponentsBuilder uriBuilder) {
        ComposerResponseDTO newComposer = service.insertComposer(composerRequestDTO);
        URI location = uriBuilder.path("/composers/{id}").buildAndExpand(newComposer.getId()).toUri();

        return ResponseEntity.created(location).body(newComposer);
    }

}
