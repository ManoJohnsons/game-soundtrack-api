package io.github.manojohnsons.gamesoundtracksapi.user;

import java.util.List;

import io.github.manojohnsons.gamesoundtracksapi.music.Music;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Setter
    private String username;

    @Column(nullable = false)
    @Setter
    private String password;

    @ManyToMany
    @JoinTable(name = "user_favorite_musics", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_music"))
    private List<Music> favoriteMusics;
}
