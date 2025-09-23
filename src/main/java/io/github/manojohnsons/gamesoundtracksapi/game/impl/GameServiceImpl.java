package io.github.manojohnsons.gamesoundtracksapi.game.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.manojohnsons.gamesoundtracksapi.game.*;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository repository;

    @Override
    public List<GameResponseDTO> searchAll() {
        List<Game> games = repository.findAll();
        return games.stream().map(GameResponseDTO::new).toList();
    }
    
}
