package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.core.finder.GameAggregateFinder;
import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import io.github.manojohnsons.gamesoundtracksapi.game.GameRepository;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumResponseDTO;

@Service
public class AlbumService {

    private final GameRepository gameRepository;
    private final GameAggregateFinder gameAggregateFinder;

    public AlbumService(GameRepository gameRepository, GameAggregateFinder gameAggregateFinder) {
        this.gameRepository = gameRepository;
        this.gameAggregateFinder = gameAggregateFinder;
    }

    @Transactional
    public AlbumResponseDTO addAlbum(Long gameId, AlbumRequestDTO albumRequestDTO) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album newAlbum = new Album(albumRequestDTO.getAlbumTitle(), game);
        game.getAlbums().add(newAlbum);
        Game gameWithAlbumSaved = gameRepository.save(game);
        Album albumSaved = gameWithAlbumSaved.getAlbums().get(gameWithAlbumSaved.getAlbums().size() - 1);

        return new AlbumResponseDTO(albumSaved);
    }

    @Transactional(readOnly = true)
    public List<AlbumResponseDTO> listAlbumsOfAGame(Long gameId) {
        Game game = gameAggregateFinder.findGameById(gameId);
        List<Album> gameAlbums = game.getAlbums();

        return gameAlbums.stream().map(AlbumResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public AlbumResponseDTO findAlbumById(Long gameId, Long albumId) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album album = gameAggregateFinder.findAlbumInGame(albumId, game);

        return new AlbumResponseDTO(album);
    }

    @Transactional
    public AlbumResponseDTO updateAlbum(Long gameId, Long albumId, AlbumRequestDTO albumRequestDTO) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album albumToUpdate = gameAggregateFinder.findAlbumInGame(albumId, game);
        albumToUpdate.setAlbumTitle(albumRequestDTO.getAlbumTitle());
        gameRepository.save(game);

        return new AlbumResponseDTO(albumToUpdate);
    }

    @Transactional
    public void deleteAlbum(Long gameId, Long albumId) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album albumToDelete = gameAggregateFinder.findAlbumInGame(albumId, game);
        game.getAlbums().remove(albumToDelete);
        gameRepository.save(game);
    }
}
