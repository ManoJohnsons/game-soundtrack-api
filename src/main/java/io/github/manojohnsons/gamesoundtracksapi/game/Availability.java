package io.github.manojohnsons.gamesoundtracksapi.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_availability")
@NoArgsConstructor
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_url", nullable = false)
    private String purchaseUrl;

    @ManyToOne
    @JoinColumn(name = "id_game", nullable = false)
    @JsonIgnore
    private Game game;

    @ManyToOne
    @JoinColumn(name = "id_platform", nullable = false)
    @JsonIgnore
    private Platform platform;
}
