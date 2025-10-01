package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.exception.ResourceNotFoundException;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.PlaybackLinkRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.PlaybackLinkResponseDTO;

@Service
public class PlaybackLinkService {

    private final MusicRepository musicRepository;

    public PlaybackLinkService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Transactional
    public PlaybackLinkResponseDTO addLink(Long musicId, PlaybackLinkRequestDTO dto) {
        Music music = findMusicById(musicId);
        PlaybackLink newPlaybackLink = new PlaybackLink(dto.getPlatformName(), dto.getMusicUrl(), music);
        music.getLinks().add(newPlaybackLink);
        musicRepository.save(music);

        return new PlaybackLinkResponseDTO(newPlaybackLink);
    }

    @Transactional(readOnly = true)
    public List<PlaybackLinkResponseDTO> listAllLinksOfAMusic(Long musicId) {
        Music music = findMusicById(musicId);
        List<PlaybackLink> links = music.getLinks();

        return links.stream().map(PlaybackLinkResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public PlaybackLinkResponseDTO getOneLinkOfAMusic(Long musicId, Long linkId) {
        Music music = findMusicById(musicId);
        PlaybackLink link = findLinkInMusic(music, linkId);

        return new PlaybackLinkResponseDTO(link);
    }

    @Transactional
    public PlaybackLinkResponseDTO updateLinkOfAMusic(Long musicId, Long linkId, PlaybackLinkRequestDTO dto) {
        Music music = findMusicById(musicId);
        PlaybackLink linkToUpdate = findLinkInMusic(music, linkId);
        linkToUpdate.setPlatformName(dto.getPlatformName());
        linkToUpdate.setMusicUrl(dto.getMusicUrl());
        musicRepository.save(music);

        return new PlaybackLinkResponseDTO(linkToUpdate);
    }

    @Transactional
    public void deleteLink(Long musicId, Long linkId) {
        Music music = findMusicById(musicId);
        PlaybackLink linkToDelete = findLinkInMusic(music, linkId);
        music.getLinks().remove(linkToDelete);
        musicRepository.save(music);
    }

    private Music findMusicById(Long musicId) {
        return musicRepository.findById(musicId)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada com o ID: " + musicId));
    }

    private PlaybackLink findLinkInMusic(Music music, Long linkId) {
        return music.getLinks().stream()
                .filter(l -> l.getId().equals(linkId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Link da música com ID: " + linkId + " não encontrado para a música com ID "+ music.getId()));
    }
}
