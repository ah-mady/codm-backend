package com.codmtracker.service;

import com.codmtracker.dto.TeamInvitationDto;
import com.codmtracker.model.Team;
import com.codmtracker.model.TeamInvitation;
import com.codmtracker.model.User;
import com.codmtracker.repository.TeamInvitationRepository;
import com.codmtracker.repository.TeamRepository;
import com.codmtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamInvitationServiceImpl implements TeamInvitationService {

    @Autowired
    private TeamInvitationRepository invitationRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendInvitation(String invitedEmail, Long teamId, Long invitedByUserId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        User inviter = userRepository.findById(invitedByUserId).orElseThrow();
        TeamInvitation invitation = TeamInvitation.builder()
                .invitedEmail(invitedEmail)
                .token(UUID.randomUUID().toString())
                .accepted(false)
                .sentAt(Instant.now())
                .team(team)
                .invitedBy(inviter)
                .build();
        invitationRepository.save(invitation);
    }

    @Override
    public void acceptInvitation(String token, Long userId) {
        TeamInvitation invitation = invitationRepository.findByToken(token).orElseThrow();
        invitation.setAccepted(true);
        invitationRepository.save(invitation);
    }

    @Override
    public List<TeamInvitationDto> getPendingInvitations(Long teamId) {
        return invitationRepository.findPendingByTeamId(teamId).stream()
                .map(inv -> TeamInvitationDto.builder()
                        .id(inv.getId())
                        .invitedEmail(inv.getInvitedEmail())
                        .token(inv.getToken())
                        .accepted(inv.isAccepted())
                        .sentAt(inv.getSentAt())
                        .teamId(inv.getTeam().getId())
                        .invitedById(inv.getInvitedBy().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void cancelInvitation(Long invitationId, Long teamId) {
        TeamInvitation invitation = invitationRepository.findByIdAndTeamId(invitationId, teamId).orElseThrow();
        invitationRepository.delete(invitation);
    }
}
