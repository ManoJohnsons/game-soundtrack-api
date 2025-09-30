package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Platform platformToInsert = new Platform(platformRequestDTO.getPlatformName());
        Platform platformCreated = platformRepository.save(platformToInsert);

        return new PlatformResponseDTO(platformCreated);
    }

    @Transactional(readOnly = true)
    public PlatformResponseDTO getPlatformById(Long platformId) {
        Platform platformFetched = platformRepository.findById(platformId).orElseThrow(NoSuchElementException::new);

        return new PlatformResponseDTO(platformFetched);
    }

    @Transactional(readOnly = true)
    public List<PlatformResponseDTO> getAllPlatforms() {
        List<Platform> allPlatforms = platformRepository.findAll();

        return allPlatforms.stream().map(PlatformResponseDTO::new).toList();
    }

    @Transactional
    public PlatformResponseDTO updatePlatform(Long platformId, PlatformRequestDTO platformRequestDTO) {
        Platform platformToUpdate = platformRepository.findById(platformId).orElseThrow(NoSuchElementException::new);
        platformToUpdate.setPlatformName(platformRequestDTO.getPlatformName());
        Platform platformUpdated = platformRepository.save(platformToUpdate);

        return new PlatformResponseDTO(platformUpdated);
    }

    @Transactional
    public void deletePlatform(Long platformId) {
        if (!platformRepository.existsById(platformId)) {
            throw new RuntimeException("Plataforma não encontrada com o ID: " + platformId);
        }
        platformRepository.deleteById(platformId);
    }

    public void addGameAvailable(Long platformId, Long gameId, AvailabilityRequestDTO availabilityRequestDTO) {
        Platform platform = platformRepository.findById(platformId)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada com ID: " + platformId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada com ID: " + gameId));
        Availability availability = new Availability(availabilityRequestDTO.getPurchaseUrl(), game, platform);
        platform.getAvailabilities().add(availability);
        platformRepository.save(platform);
    }

}
