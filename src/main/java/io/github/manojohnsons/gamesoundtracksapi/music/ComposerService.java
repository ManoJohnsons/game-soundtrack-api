package io.github.manojohnsons.gamesoundtracksapi.music;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;

public interface ComposerService {
    
    ComposerResponseDTO insertComposer(ComposerRequestDTO composerDTO);
}
