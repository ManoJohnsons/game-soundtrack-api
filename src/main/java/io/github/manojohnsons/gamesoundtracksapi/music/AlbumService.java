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
        Game game = findGameById(gameId);
        Album newAlbum = new Album(albumRequestDTO.getAlbumTitle(), game);
        game.getAlbums().add(newAlbum);
        Game gameWithAlbumSaved = gameRepository.save(game);
        Album albumSaved = gameWithAlbumSaved.getAlbums().get(gameWithAlbumSaved.getAlbums().size() - 1);

        return new AlbumResponseDTO(albumSaved);
    }

    @Transactional(readOnly = true)
    public List<AlbumResponseDTO> listAlbumsOfAGame(Long gameId) {
        Game game = findGameById(gameId);
        List<Album> gameAlbums = game.getAlbums();

        return gameAlbums.stream().map(AlbumResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public AlbumResponseDTO findAlbumById(Long gameId, Long albumId) {
        Game game = findGameById(gameId);
        Album album = findAlbumInGame(albumId, game);

        return new AlbumResponseDTO(album);
    }

    @Transactional
    public AlbumResponseDTO updateAlbum(Long gameId, Long albumId, AlbumRequestDTO albumRequestDTO) {
        Game game = findGameById(gameId);
        Album albumToUpdate = findAlbumInGame(albumId, game);
        albumToUpdate.setAlbumTitle(albumRequestDTO.getAlbumTitle());
        gameRepository.save(game);

        return new AlbumResponseDTO(albumToUpdate);
    }

    @Transactional
    public void deleteAlbum(Long gameId, Long albumId) {
        Game game = findGameById(gameId);
        Album albumToDelete = findAlbumInGame(albumId, game);
        game.getAlbums().remove(albumToDelete);
        gameRepository.save(game);
    }

    private Album findAlbumInGame(Long albumId, Game game) {
        return game.getAlbums().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Album com ID " + albumId + " não encontrado para o jogo com ID " + game.getId()));
    }

    private Game findGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
    }
}
