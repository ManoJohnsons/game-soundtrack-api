package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.exception.ResourceAlreadyExistsException;
import io.github.manojohnsons.gamesoundtracksapi.exception.ResourceNotFoundException;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.AvailabilityRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformResponseDTO;

@Service
public class PlatformService {

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public PlatformResponseDTO insertPlatform(PlatformRequestDTO platformRequestDTO) {
        if (platformRepository.existsByPlatformName(platformRequestDTO.getPlatformName())) {
            throw new ResourceAlreadyExistsException(
                    "Nome da plataforma '" + platformRequestDTO.getPlatformName() + "' já está em registrado.");
        }
        Platform platformToInsert = new Platform(platformRequestDTO.getPlatformName());
        Platform platformCreated = platformRepository.save(platformToInsert);

        return new PlatformResponseDTO(platformCreated);
    }

    @Transactional(readOnly = true)
    public PlatformResponseDTO getPlatformById(Long platformId) {
        Platform platformFetched = platformRepository.findById(platformId)
                .orElseThrow(() -> new ResourceNotFoundException("Plataforma não encontrada com o ID: " + platformId));

        return new PlatformResponseDTO(platformFetched);
    }

    @Transactional(readOnly = true)
    public List<PlatformResponseDTO> getAllPlatforms() {
        List<Platform> allPlatforms = platformRepository.findAll();

        return allPlatforms.stream().map(PlatformResponseDTO::new).toList();
    }

    @Transactional
    public PlatformResponseDTO updatePlatform(Long platformId, PlatformRequestDTO platformRequestDTO) {
        Platform platformToUpdate = platformRepository.findById(platformId)
                .orElseThrow(() -> new ResourceNotFoundException("Plataforma não encontrada com o ID: " + platformId));
        if (!platformToUpdate.getPlatformName().equals(platformRequestDTO.getPlatformName()) &&
                platformRepository.existsByPlatformName(platformRequestDTO.getPlatformName())) {
            throw new ResourceAlreadyExistsException(
                    "Nome da plataforma '" + platformRequestDTO.getPlatformName() + "' já está em registrado.");
        }
        platformToUpdate.setPlatformName(platformRequestDTO.getPlatformName());
        Platform platformUpdated = platformRepository.save(platformToUpdate);

        return new PlatformResponseDTO(platformUpdated);
    }

    @Transactional
    public void deletePlatform(Long platformId) {
        if (!platformRepository.existsById(platformId)) {
            throw new ResourceNotFoundException("Plataforma não encontrada com o ID: " + platformId);
        }
        platformRepository.deleteById(platformId);
    }

    public void addGameAvailable(Long platformId, Long gameId, AvailabilityRequestDTO availabilityRequestDTO) {
        Platform platform = platformRepository.findById(platformId)
                .orElseThrow(() -> new ResourceNotFoundException("Plataforma não encontrada com o ID: " + platformId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Jogo não encontrado com o ID: " + gameId));
        Availability availability = new Availability(availabilityRequestDTO.getPurchaseUrl(), game, platform);
        platform.getAvailabilities().add(availability);
        platformRepository.save(platform);
    }

}
