package io.github.manojohnsons.gamesoundtracksapi.music.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.music.*;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;

@Service
public class ComposerServiceImpl implements ComposerService {

    @Autowired
    private ComposerRepository repository;

    @Override
    @Transactional
    public ComposerResponseDTO insertComposer(ComposerRequestDTO composerDTO) {
        Composer composerToAdd = new Composer();

        composerToAdd.setName(composerDTO.getName());

        Composer composerSaved = repository.save(composerToAdd);

        return new ComposerResponseDTO(composerSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComposerResponseDTO> searchAllComposers() {
        List<Composer> allComposers = repository.findAll();
        return allComposers.stream().map(ComposerResponseDTO::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ComposerResponseDTO searchComposerById(Long id) {
        Composer composerFetched = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return new ComposerResponseDTO(composerFetched);
    }

    @Override
    @Transactional
    public ComposerResponseDTO updateComposerById(Long id, ComposerRequestDTO composerDTO) {
        Composer composerToUpdate = repository.findById(id).orElseThrow(NoSuchElementException::new);

        composerToUpdate.setName(composerDTO.getName());

        Composer composerUpdated = repository.save(composerToUpdate);

        return new ComposerResponseDTO(composerUpdated);
    }

    @Override
    @Transactional
    public void deleteComposerById(Long id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("Compositor/Artista não encontrado com o ID: " + id);
        }

        repository.deleteById(id);
    }

}
