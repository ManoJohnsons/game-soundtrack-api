package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import io.github.manojohnsons.gamesoundtracksapi.game.GameRepository;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumResponseDTO;

@Service
public class AlbumService {

    private final GameRepository gameRepository;

    public AlbumService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public AlbumResponseDTO addAlbum(Long gameId, AlbumRequestDTO albumRequestDTO) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
        Album newAlbum = new Album(albumRequestDTO.getAlbumTitle(), game);
        game.getAlbuns().add(newAlbum);
        Game gameWithAlbumSaved = gameRepository.save(game);
        Album albumSaved = gameWithAlbumSaved.getAlbuns().get(gameWithAlbumSaved.getAlbuns().size() - 1);

        return new AlbumResponseDTO(albumSaved);
    }

    @Transactional(readOnly = true)
    public List<AlbumResponseDTO> listAlbunsOfAGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
        List<Album> gameAlbums = game.getAlbuns();

        return gameAlbums.stream().map(AlbumResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public AlbumResponseDTO searchAlbumById(Long gameId, Long albumId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
        Album album = game.getAlbuns().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Album com ID " + albumId + " não encontrado para o jogo com ID " + gameId));

        return new AlbumResponseDTO(album);
    }

    @Transactional
    public AlbumResponseDTO updateAlbum(Long gameId, Long albumId, AlbumRequestDTO albumRequestDTO) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
        Album albumToUpdate = game.getAlbuns().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Album com ID " + albumId + " não encontrado para o jogo com ID " + gameId));
        albumToUpdate.setAlbumTitle(albumRequestDTO.getAlbumTitle());
        gameRepository.save(game);

        return new AlbumResponseDTO(albumToUpdate);
    }

    @Transactional
    public void deleteAlbum(Long gameId, Long albumId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
        Album albumToDelete = game.getAlbuns().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Album com ID " + albumId + " não encontrado para o jogo com ID " + gameId));
        game.getAlbuns().remove(albumToDelete);
        gameRepository.save(game);
    }
}
