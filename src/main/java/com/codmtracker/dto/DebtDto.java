package com.codmtracker.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DebtDto {
    private Long id;
    private Long playerId;
    private int debtAmount;
    private Instant lastUpdated;
}
