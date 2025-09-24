package io.github.manojohnsons.gamesoundtracksapi.game;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService service;

    @GetMapping
    public ResponseEntity<List<GameResponseDTO>> searchAll() {
        List<GameResponseDTO> games = service.searchAll();
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<GameResponseDTO> insertGame(@RequestBody @Valid GameRequestDTO gameRequestDTO,
            UriComponentsBuilder uriBuilder) {
        GameResponseDTO newGame = service.insertGame(gameRequestDTO);
        URI location = uriBuilder.path("/games/{id}").buildAndExpand(newGame.getId()).toUri();
        return ResponseEntity.created(location).body(newGame);
    }

}
