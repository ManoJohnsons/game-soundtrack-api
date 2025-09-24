package io.github.manojohnsons.gamesoundtracksapi.music.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.manojohnsons.gamesoundtracksapi.music.*;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;

@Service
public class ComposerServiceImpl implements ComposerService {

    @Autowired
    private ComposerRepository repository;

    @Override
    public ComposerResponseDTO insertComposer(ComposerRequestDTO composerDTO) {
        Composer composerToAdd = new Composer();

        composerToAdd.setName(composerDTO.getName());

        Composer composerSaved = repository.save(composerToAdd);

        return new ComposerResponseDTO(composerSaved);
    }

}
