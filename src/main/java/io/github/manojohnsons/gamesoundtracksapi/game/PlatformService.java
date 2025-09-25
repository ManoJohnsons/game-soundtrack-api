package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.List;

import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformResponseDTO;

public interface PlatformService {
    
    PlatformResponseDTO insertPlatform(PlatformRequestDTO platformRequestDTO);

    PlatformResponseDTO getPlatformById(Long id);

    List<PlatformResponseDTO> getAllPlatforms();

    PlatformResponseDTO updatePlatform(Long id, PlatformRequestDTO platformRequestDTO);

    void deletePlatform(Long id);
}
