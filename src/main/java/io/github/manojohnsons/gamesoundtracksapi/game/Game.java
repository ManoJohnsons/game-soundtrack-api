package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.Date;
import java.util.List;

import io.github.manojohnsons.gamesoundtracksapi.music.Album;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_game")
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_title", nullable = false)
    private String gameTitle;

    @Column(nullable = false)
    private String developer;

    @Column(nullable = false)
    private String publisher;

    @Column(name = "release_year", nullable = false)
    private Date releaseYear;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albuns;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities;
}
