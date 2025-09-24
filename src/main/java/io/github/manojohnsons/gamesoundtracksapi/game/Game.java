package io.github.manojohnsons.gamesoundtracksapi.game;

import java.util.Date;
import java.util.List;

import io.github.manojohnsons.gamesoundtracksapi.music.Album;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_game")
@NoArgsConstructor
@Getter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_title", nullable = false)
    @Setter
    private String gameTitle;

    @Column(nullable = false)
    @Setter
    private String developer;

    @Column(nullable = false)
    @Setter
    private String publisher;

    @Column(name = "release_year", nullable = false)
    @Setter
    private Date releaseYear;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albuns;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities;
}
