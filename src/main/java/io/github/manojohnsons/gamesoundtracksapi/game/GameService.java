package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;

@Service
public class GameService {

    @Autowired
    private GameRepository repository;

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

    @Transactional(readOnly = true)
    public List<GameResponseDTO> searchAllGames() {
        List<Game> allGames = repository.findAll();

        return allGames.stream().map(GameResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public GameResponseDTO searchGameById(Long id) {
        Game gameFetched = repository.findById(id).orElseThrow(NoSuchElementException::new);

        return new GameResponseDTO(gameFetched);
    }

    @Transactional
    public GameResponseDTO updateGameById(Long id, GameRequestDTO dto) {
        Game gameToUpdate = repository.findById(id).orElseThrow(NoSuchElementException::new);
        gameToUpdate.setGameTitle(dto.getGameTitle());
        gameToUpdate.setDeveloper(dto.getDeveloper());
        gameToUpdate.setPublisher(dto.getPublisher());
        gameToUpdate.setReleaseYear(dto.getReleaseYear());
        Game gameUpdated = repository.save(gameToUpdate);

        return new GameResponseDTO(gameUpdated);
    }

    @Transactional
    public void deleteGameById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Jogo não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

}
