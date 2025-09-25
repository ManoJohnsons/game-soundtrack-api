package io.github.manojohnsons.gamesoundtracksapi.game;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService service;

    @PostMapping
    public ResponseEntity<GameResponseDTO> insertGame(@RequestBody @Valid GameRequestDTO gameRequestDTO,
            UriComponentsBuilder uriBuilder) {
        GameResponseDTO newGame = service.insertGame(gameRequestDTO);
        URI location = uriBuilder.path("/games/{id}").buildAndExpand(newGame.getId()).toUri();
        
        return ResponseEntity.created(location).body(newGame);
    }

    @GetMapping
    public ResponseEntity<List<GameResponseDTO>> searchAllGames() {
        List<GameResponseDTO> games = service.searchAllGames();
        
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> searchGameById(@PathVariable Long id) {
        var gameFetched = service.searchGameById(id);
        
        return ResponseEntity.ok(gameFetched);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDTO> updateGame(@PathVariable Long id,
            @RequestBody @Valid GameRequestDTO gameRequestDTO) {
        GameResponseDTO gameToUpdate = service.updateGameById(id, gameRequestDTO);
        
        return ResponseEntity.ok(gameToUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable Long id) {
        service.deleteGameById(id);
    }
}
