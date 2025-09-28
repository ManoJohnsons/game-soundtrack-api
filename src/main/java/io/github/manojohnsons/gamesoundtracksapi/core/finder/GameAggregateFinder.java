package io.github.manojohnsons.gamesoundtracksapi.core.finder;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import io.github.manojohnsons.gamesoundtracksapi.game.GameRepository;
import io.github.manojohnsons.gamesoundtracksapi.music.Album;
import io.github.manojohnsons.gamesoundtracksapi.music.Music;

@Service
public class GameAggregateFinder {

    private final GameRepository gameRepository;

    public GameAggregateFinder(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game findGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
    }

    public Album findAlbumInGame(Long albumId, Game game) {
        return findOptionalAlbumInGame(albumId, game)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Album com ID " + albumId + " não encontrado para o jogo com ID " + game.getId()));
    }

    public Album getAlbumInGame(Long albumId, Game game) {
        return findOptionalAlbumInGame(albumId, game)
                .get();
    }

    public Music findMusicInAlbum(Long musicId, Album album) {
        return album.getMusics().stream()
                .filter(m -> m.getId().equals(musicId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Música com ID " + musicId + " não encontrada para o álbum com ID " + album.getId()));
    }

    private Optional<Album> findOptionalAlbumInGame(Long albumId, Game game) {
        return game.getAlbums().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst();
    }
}
