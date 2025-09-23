package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;

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
}
