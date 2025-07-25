package com.codmtracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class KillTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Player fromPlayer;

    @ManyToOne
    private Player toPlayer;

    private int transferredKills;

    @ManyToOne
    private Game game;

    private Instant transferredAt;
}
