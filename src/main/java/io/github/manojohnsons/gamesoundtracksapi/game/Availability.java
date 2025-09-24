package io.github.manojohnsons.gamesoundtracksapi.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tab_availability")
@NoArgsConstructor
@Getter
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_url", nullable = false)
    @Setter
    private String purchaseUrl;

    @ManyToOne
    @JoinColumn(name = "id_game", nullable = false)
    @JsonIgnore
    @Setter
    private Game game;

    @ManyToOne
    @JoinColumn(name = "id_platform", nullable = false)
    @JsonIgnore
    @Setter
    private Platform platform;
}
