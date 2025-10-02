package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicFilterDTO;

@Component
public class MusicSpecificationBuilder {
    
    public Specification<Music> build(MusicFilterDTO filterDTO) {
        Specification<Music> spec = Specification.unrestricted();

        if (filterDTO.getComposerName() != null && !filterDTO.getComposerName().isBlank()) {
            spec = spec.and(MusicSpecifications.fromComposer(filterDTO.getComposerName()));
        }

        if (filterDTO.getAlbumTitle() != null && !filterDTO.getAlbumTitle().isBlank()) {
            spec = spec.and(MusicSpecifications.fromAlbum(filterDTO.getAlbumTitle()));
        }

        if (filterDTO.getGameTitle() != null && !filterDTO.getGameTitle().isBlank()) {
            spec = spec.and(MusicSpecifications.fromGame(filterDTO.getGameTitle()));
        }

        return spec;
    }
}
