package io.github.manojohnsons.gamesoundtracksapi.music;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_playback_links")
@NoArgsConstructor
public class PlaybackLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform_name", nullable = false)
    private String platformName;

    @Column(name = "music_url", nullable = false)
    private String musicUrl;

    @ManyToOne
    @JoinColumn(name = "id_music", nullable = false)
    @JsonIgnore
    private Music music;
}
