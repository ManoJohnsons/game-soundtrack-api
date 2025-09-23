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
public class Composer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Column(nullable = false)
    @Getter @Setter private String name;

    @ManyToMany(mappedBy = "composers")
    @JsonIgnore
    @Getter @Setter private List<Music> musics;
}
