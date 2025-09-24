package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_album")
@NoArgsConstructor
@Getter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "album_title", nullable = false)
    @Setter
    private String albumTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_game", nullable = false)
    @JsonIgnore
    @Setter
    private Game game;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Music> musics;
}
