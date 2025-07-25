package com.codmtracker.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {
    private Long id;
    private String name;
    private Instant createdAt;
    private Long ownerId;
}
