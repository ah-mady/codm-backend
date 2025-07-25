package com.codmtracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String map;
    private boolean victory;
    private Instant playedAt;
    private String mvpPlayerName;
    private String mode;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<GameParticipant> participants;
}
