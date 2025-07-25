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
public class TeamInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invitedEmail;
    private String token;
    private boolean accepted;
    private Instant sentAt;

    @ManyToOne
    private Team team;

    @ManyToOne
    private User invitedBy;
}
