package com.codmtracker.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KillDto {
    private Long playerId;
    private int kills;
    private boolean mvp;
}
