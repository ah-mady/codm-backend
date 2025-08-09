package com.codmtracker.service;

import com.codmtracker.model.TeamInvitation;

import java.util.List;

public interface TeamInvitationService {
    TeamInvitation createInvite(Long teamId, String invitedEmail, String role, String inviterEmail);

    void cancelInvite(Long teamId, Long invitationId, String actorEmail);

    List<TeamInvitation> listPendingInvites(Long teamId, String actorEmail);

    void acceptInvite(String token, String actorEmail);
}
