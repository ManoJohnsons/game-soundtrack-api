package io.github.manojohnsons.gamesoundtracksapi.game.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.game.*;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformResponseDTO;

@Service
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformRepository repository;

    @Override
    @Transactional
    public PlatformResponseDTO insertPlatform(PlatformRequestDTO platformRequestDTO) {
        Platform platformToInsert = new Platform();

        platformToInsert.setPlatformName(platformRequestDTO.getPlatformName());

        Platform platformCreated = repository.save(platformToInsert);

        return new PlatformResponseDTO(platformCreated);
    }

    @Override
    @Transactional(readOnly = true)
    public PlatformResponseDTO getPlatformById(Long id) {
        Platform platformFetched = repository.findById(id).orElseThrow(NoSuchElementException::new);
        return new PlatformResponseDTO(platformFetched);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatformResponseDTO> getAllPlatforms() {
        List<Platform> allPlatforms = repository.findAll();
        return allPlatforms.stream().map(PlatformResponseDTO::new).toList();
    }

    @Override
    @Transactional
    public PlatformResponseDTO updatePlatform(Long id, PlatformRequestDTO platformRequestDTO) {
        Platform platformToUpdate = repository.findById(id).orElseThrow(NoSuchElementException::new);

        platformToUpdate.setPlatformName(platformRequestDTO.getPlatformName());

        Platform platformUpdated = repository.save(platformToUpdate);

        return new PlatformResponseDTO(platformUpdated);
    }

    @Override
    @Transactional
    public void deletePlatform(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Plataforma não encontrada com o ID: " + id);
        }

        repository.deleteById(id);
    }

}
