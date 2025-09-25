package io.github.manojohnsons.gamesoundtracksapi.game;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformResponseDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/platforms")
public class PlatformController {

    @Autowired
    private PlatformService service;

    @PostMapping
    public ResponseEntity<PlatformResponseDTO> insertPlatform(@RequestBody @Valid PlatformRequestDTO platformRequestDTO,
            UriComponentsBuilder uriBuilder) {
        PlatformResponseDTO newPlatform = service.insertPlatform(platformRequestDTO);
        URI location = uriBuilder.path("/platforms/{id}").buildAndExpand(newPlatform.getId()).toUri();
        return ResponseEntity.created(location).body(newPlatform);
    }

    @GetMapping
    public ResponseEntity<List<PlatformResponseDTO>> getAllPlatforms() {
        List<PlatformResponseDTO> allPlatforms = service.getAllPlatforms();
        return ResponseEntity.ok(allPlatforms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> getPlatformById(@PathVariable Long id) {
        PlatformResponseDTO platformFetched = service.getPlatformById(id);
        return ResponseEntity.ok(platformFetched);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> updatePlatform(@PathVariable Long id,
            @RequestBody @Valid PlatformRequestDTO platformRequestDTO) {
        PlatformResponseDTO platformUpdated = service.updatePlatform(id, platformRequestDTO);
        return ResponseEntity.ok(platformUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePlatform(@PathVariable Long id) {
        service.deletePlatform(id);
    }
}
