package com.codmtracker.service;

import com.codmtracker.dto.TeamInvitationDto;

import java.util.List;

public interface TeamInvitationService {
    void sendInvitation(String invitedEmail, Long teamId, Long invitedByUserId);

    void acceptInvitation(String token, Long userId);

    List<TeamInvitationDto> getPendingInvitations(Long teamId);

    void cancelInvitation(Long invitationId, Long teamId);
}
