package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.List;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;

public interface GameService {

    public List<GameResponseDTO> searchAll();

    public GameResponseDTO insertGame(GameRequestDTO dto);

    public GameResponseDTO searchGameById(Long id);
}
