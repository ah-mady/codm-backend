package com.codmtracker.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportResultDto {
    private int gamesImported;
    private int playersImported;
    private int errors;
}
