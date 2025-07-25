package com.codmtracker.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codmName;
    private String roleType;
    private boolean active;

    @ManyToOne
    private Team team;

    @ManyToOne
    private User user;
}
