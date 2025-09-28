package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.core.finder.GameAggregateFinder;
import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import io.github.manojohnsons.gamesoundtracksapi.game.GameRepository;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicResponseDTO;

@Service
public class MusicService {

    private final GameRepository gameRepository;
    private final GameAggregateFinder gameAggregateFinder;

    public MusicService(GameRepository gameRepository, GameAggregateFinder gameAggregateFinder) {
        this.gameRepository = gameRepository;
        this.gameAggregateFinder = gameAggregateFinder;
    }

    @Transactional
    public MusicResponseDTO addMusic(Long gameId, Long albumId, MusicRequestDTO musicRequestDTO) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album album = gameAggregateFinder.findAlbumInGame(albumId, game);
        Music musicToAdd = new Music(musicRequestDTO.getMusicTitle(), musicRequestDTO.getDurationInSeconds(), album);
        album.getMusics().add(musicToAdd);

        Game gameWithMusicSaved = gameRepository.save(game);
        Album albumWithMusicSaved = gameAggregateFinder.getAlbumInGame(albumId, gameWithMusicSaved);
        Music musicSaved = albumWithMusicSaved.getMusics().get(albumWithMusicSaved.getMusics().size() - 1);

        return new MusicResponseDTO(musicSaved);
    }

    @Transactional(readOnly = true)
    public List<MusicResponseDTO> listMusicsFromAlbum(Long gameId, Long albumId) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album album = gameAggregateFinder.findAlbumInGame(albumId, game);

        return album.getMusics().stream().map(MusicResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public MusicResponseDTO findMusicById(Long gameId, Long albumId, Long musicId) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album album = gameAggregateFinder.findAlbumInGame(albumId, game);
        Music music = gameAggregateFinder.findMusicInAlbum(musicId, album);

        return new MusicResponseDTO(music);
    }

    @Transactional
    public MusicResponseDTO updateMusic(Long gameId, Long albumId, Long musicId, MusicRequestDTO musicRequestDTO) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album album = gameAggregateFinder.findAlbumInGame(albumId, game);
        Music musicToUpdate = gameAggregateFinder.findMusicInAlbum(musicId, album);
        musicToUpdate.setMusicTitle(musicRequestDTO.getMusicTitle());
        musicToUpdate.setDurationInSeconds(musicRequestDTO.getDurationInSeconds());
        gameRepository.save(game);

        return new MusicResponseDTO(musicToUpdate);
    }

    @Transactional
    public void deleteMusic(Long gameId, Long albumId, Long musicId) {
        Game game = gameAggregateFinder.findGameById(gameId);
        Album album = gameAggregateFinder.findAlbumInGame(albumId, game);
        Music musicToDelete = gameAggregateFinder.findMusicInAlbum(musicId, album);
        album.getMusics().remove(musicToDelete);
        gameRepository.save(game);
    }
}
