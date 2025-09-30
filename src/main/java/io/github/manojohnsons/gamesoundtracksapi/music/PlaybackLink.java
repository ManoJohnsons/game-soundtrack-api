package io.github.manojohnsons.gamesoundtracksapi.music;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_playback_links")
@NoArgsConstructor
@Getter
public class PlaybackLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "platform_name", nullable = false)
    @Setter
    private String platformName;

    @Column(name = "music_url", nullable = false)
    @Setter
    private String musicUrl;

    @ManyToOne
    @JoinColumn(name = "id_music", nullable = false)
    @JsonIgnore
    private Music music;

    public PlaybackLink(String platformName, String musicUrl, Music music) {
        this.platformName = platformName;
        this.musicUrl = musicUrl;
        this.music = music;
    }
}
