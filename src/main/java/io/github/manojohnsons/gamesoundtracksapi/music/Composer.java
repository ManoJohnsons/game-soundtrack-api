package io.github.manojohnsons.gamesoundtracksapi.music;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_composer")
@NoArgsConstructor
@Getter
public class Composer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @ManyToMany(mappedBy = "composers")
    @JsonIgnore
    private List<Music> musics;
}
