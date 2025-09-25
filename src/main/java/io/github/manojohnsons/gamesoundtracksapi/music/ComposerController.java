package io.github.manojohnsons.gamesoundtracksapi.music;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;
import jakarta.validation.Valid;

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

    @GetMapping
    public ResponseEntity<List<ComposerResponseDTO>> searchAllComposers() {
        List<ComposerResponseDTO> composers = service.searchAllComposers();
        
        return ResponseEntity.ok(composers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComposerResponseDTO> searchComposerById(@PathVariable Long id) {
        ComposerResponseDTO composerFetched = service.searchComposerById(id);
        
        return ResponseEntity.ok(composerFetched);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComposerResponseDTO> updateComposer(@PathVariable Long id,
            @RequestBody @Valid ComposerRequestDTO composerRequestDTO) {
        ComposerResponseDTO composerToUpdate = service.updateComposerById(id, composerRequestDTO);

        return ResponseEntity.ok(composerToUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComposer(@PathVariable Long id) {
        service.deleteComposerById(id);
    }
}
