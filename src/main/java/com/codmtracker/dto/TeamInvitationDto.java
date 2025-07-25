package com.codmtracker.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamInvitationDto {
    private Long id;
    private String invitedEmail;
    private String token;
    private boolean accepted;
    private Instant sentAt;
    private Long teamId;
    private Long invitedById;
}
