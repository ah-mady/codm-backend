package com.codmtracker.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDto {
    private Long id;
    private String codmName;
    private String roleType;
    private boolean active;
    private Long teamId;
    private Long userId;
}
