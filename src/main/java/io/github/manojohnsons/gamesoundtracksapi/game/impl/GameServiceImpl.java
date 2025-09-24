package io.github.manojohnsons.gamesoundtracksapi.game.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.game.*;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<GameResponseDTO> searchAll() {
        List<Game> games = repository.findAll();
        return games.stream().map(GameResponseDTO::new).toList();
    }

    @Override
    @Transactional
    public GameResponseDTO insertGame(GameRequestDTO gameDTO) {
        Game gameToCreate = new Game();

        gameToCreate.setGameTitle(gameDTO.getGameTitle());
        gameToCreate.setDeveloper(gameDTO.getDeveloper());
        gameToCreate.setPublisher(gameDTO.getPublisher());
        gameToCreate.setReleaseYear(gameDTO.getReleaseYear());

        Game gameSaved = repository.save(gameToCreate);

        return new GameResponseDTO(gameSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public GameResponseDTO searchGameById(Long id) {
        Game gameFetched = repository.findById(id).orElseThrow(NoSuchElementException::new);

        return new GameResponseDTO(gameFetched);
    }

}
