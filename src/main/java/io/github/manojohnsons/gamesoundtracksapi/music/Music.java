package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_music")
@NoArgsConstructor
@Getter
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "music_title", nullable = false)
    @Setter
    private String musicTitle;

    @Column(name = "duration_in_seconds", nullable = false)
    @Setter
    private Integer durationInSeconds;

    @ManyToOne
    @JoinColumn(name = "id_album", nullable = false)
    @JsonIgnore
    @Setter
    private Album album;

    @ManyToMany
    @JoinTable(name = "musics_composers", joinColumns = @JoinColumn(name = "id_music"), inverseJoinColumns = @JoinColumn(name = "id_composer"))
    private List<Composer> composers;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaybackLink> links;

    public Music(String musicTitle, Integer durationInSeconds, Album album) {
        this.musicTitle = musicTitle;
        this.durationInSeconds = durationInSeconds;
        this.album = album;
    }
}
