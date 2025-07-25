package com.codmtracker.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OcrResultDto {
    private String map;
    private String mode;
    private boolean victory;
    private String mvpPlayerName;
    private List<KillDto> participants;
}
