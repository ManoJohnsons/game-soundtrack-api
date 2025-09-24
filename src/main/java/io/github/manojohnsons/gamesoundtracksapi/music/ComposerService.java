package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;

public interface ComposerService {
    
    ComposerResponseDTO insertComposer(ComposerRequestDTO composerDTO);

    List<ComposerResponseDTO> searchAllComposers();

    ComposerResponseDTO searchComposerById(Long id);

    ComposerResponseDTO updateComposerById(Long id, ComposerRequestDTO composerDTO);

    void deleteComposerById(Long id);
}
