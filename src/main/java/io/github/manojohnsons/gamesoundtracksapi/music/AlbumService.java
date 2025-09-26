package io.github.manojohnsons.gamesoundtracksapi.music;

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
}
