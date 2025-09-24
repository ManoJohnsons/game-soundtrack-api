package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.List;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;

public interface GameService {

    public GameResponseDTO insertGame(GameRequestDTO gameDTO);
    
    public List<GameResponseDTO> searchAllGames();

    public GameResponseDTO searchGameById(Long id);

    public GameResponseDTO updateGameById(Long id, GameRequestDTO gameDTO);

    public void deleteGameById(Long id);
}
