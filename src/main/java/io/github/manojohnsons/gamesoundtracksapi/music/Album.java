package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.manojohnsons.gamesoundtracksapi.game.Game;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_album")
@NoArgsConstructor
@Data
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "album_title", nullable = false)
    private String albumTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_game", nullable = false)
    @JsonIgnore
    private Game game;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Music> musics;
}
