package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import io.github.manojohnsons.gamesoundtracksapi.game.GameRepository;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumResponseDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicResponseDTO;

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

    @Transactional
    public MusicResponseDTO addMusic(Long gameId, Long albumId, MusicRequestDTO musicRequestDTO) {
        Game game = findGameById(gameId);
        Album album = findAlbumInGame(albumId, game);
        Music musicToAdd = new Music(musicRequestDTO.getMusicTitle(), musicRequestDTO.getDurationInSeconds(), album);
        album.getMusics().add(musicToAdd);

        Game gameWithMusicSaved = gameRepository.save(game);
        Album albumWithMusicSaved = getAlbumInGame(albumId, gameWithMusicSaved);
        Music musicSaved = albumWithMusicSaved.getMusics().get(albumWithMusicSaved.getMusics().size() - 1);

        return new MusicResponseDTO(musicSaved);
    }

    @Transactional(readOnly = true)
    public List<MusicResponseDTO> listMusicsFromAlbum(Long gameId, Long albumId) {
        Game game = findGameById(gameId);
        Album album = findAlbumInGame(albumId, game);

        return album.getMusics().stream().map(MusicResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public MusicResponseDTO findMusicById(Long gameId, Long albumId, Long musicId) {
        Game game = findGameById(gameId);
        Album album = findAlbumInGame(albumId, game);
        Music music = findMusicInAlbum(musicId, album);

        return new MusicResponseDTO(music);
    }

    @Transactional
    public MusicResponseDTO updateMusic(Long gameId, Long albumId, Long musicId, MusicRequestDTO musicRequestDTO) {
        Game game = findGameById(gameId);
        Album album = findAlbumInGame(albumId, game);
        Music musicToUpdate = findMusicInAlbum(musicId, album);
        musicToUpdate.setMusicTitle(musicRequestDTO.getMusicTitle());
        musicToUpdate.setDurationInSeconds(musicRequestDTO.getDurationInSeconds());
        gameRepository.save(game);

        return new MusicResponseDTO(musicToUpdate);
    }

    @Transactional
    public void deleteMusic(Long gameId, Long albumId, Long musicId) {
        Game game = findGameById(gameId);
        Album album = findAlbumInGame(albumId, game);
        Music musicToDelete = findMusicInAlbum(musicId, album);
        album.getMusics().remove(musicToDelete);
        gameRepository.save(game);
    }

    private Album findAlbumInGame(Long albumId, Game game) {
        return findOptionalAlbumInGame(albumId, game)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Album com ID " + albumId + " não encontrado para o jogo com ID " + game.getId()));
    }

    private Album getAlbumInGame(Long albumId, Game game) {
        return findOptionalAlbumInGame(albumId, game)
                .get();
    }

    private Optional<Album> findOptionalAlbumInGame(Long albumId, Game game) {
        return game.getAlbums().stream()
                .filter(a -> a.getId().equals(albumId))
                .findFirst();
    }

    private Game findGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com o ID: " + gameId));
    }

    private Music findMusicInAlbum(Long musicId, Album album) {
        return album.getMusics().stream()
                .filter(m -> m.getId().equals(musicId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Música com ID " + musicId + " não encontrada para o álbum com ID " + album.getId()));
    }
}
