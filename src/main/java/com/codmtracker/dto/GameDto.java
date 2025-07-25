package com.codmtracker.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDto {
    private Long id;
    private String map;
    private boolean victory;
    private String mode;
    private Instant playedAt;
    private String mvpPlayerName;
    private Long teamId;
    private List<KillDto> participants;
}
