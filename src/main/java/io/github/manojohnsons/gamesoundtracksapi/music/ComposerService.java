package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.exception.ResourceNotFoundException;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;

@Service
public class ComposerService {

    @Autowired
    private ComposerRepository repository;

    @Transactional
    public ComposerResponseDTO insertComposer(ComposerRequestDTO composerDTO) {
        Composer composerToAdd = new Composer(composerDTO.getName());
        Composer composerSaved = repository.save(composerToAdd);

        return new ComposerResponseDTO(composerSaved);
    }

    @Transactional(readOnly = true)
    public List<ComposerResponseDTO> searchAllComposers() {
        List<Composer> allComposers = repository.findAll();

        return allComposers.stream().map(ComposerResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ComposerResponseDTO searchComposerById(Long id) {
        Composer composerFetched = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artista/Compositor não encontrado com o ID: " + id));

        return new ComposerResponseDTO(composerFetched);
    }

    @Transactional
    public ComposerResponseDTO updateComposerById(Long id, ComposerRequestDTO composerDTO) {
        Composer composerToUpdate = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artista/Compositor não encontrado com o ID: " + id));
        composerToUpdate.setName(composerDTO.getName());
        Composer composerUpdated = repository.save(composerToUpdate);

        return new ComposerResponseDTO(composerUpdated);
    }

    @Transactional
    public void deleteComposerById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Artista/Compositor não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }

}
