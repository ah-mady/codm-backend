package com.codmtracker.service;

import com.codmtracker.exception.CustomException;
import com.codmtracker.model.Team;
import com.codmtracker.model.TeamInvitation;
import com.codmtracker.model.User;
import com.codmtracker.repository.TeamInvitationRepository;
import com.codmtracker.repository.TeamRepository;
import com.codmtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class TeamInvitationServiceImpl implements TeamInvitationService {

    @Autowired
    private TeamInvitationRepository teamInvitationRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public TeamInvitation createInvite(Long teamId, String invitedEmail, String role, String inviterEmail) {
        if (teamId == null) throw new CustomException("TeamId required", 400);
        if (invitedEmail == null || invitedEmail.isBlank()) throw new CustomException("Email required", 400);
        if (role == null || role.isBlank()) throw new CustomException("Role required", 400);

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException("Team not found", 404));
        User inviter = userRepository.findByEmail(inviterEmail).orElseThrow(() -> new CustomException("User not found", 404));

        enforceCanManageTeam(inviter, team);

        String token = UUID.randomUUID().toString();
        TeamInvitation inv = TeamInvitation.builder()
                .team(team)
                .invitedEmail(invitedEmail.trim().toLowerCase())
                .token(token)
                .accepted(false)
                .role(role.toUpperCase())
                .createdAt(Instant.now())
                .invitedBy(inviter)
                .build();

        teamInvitationRepository.save(inv);
        emailService.sendTeamInviteEmail(inv.getInvitedEmail(), token, team.getName());

        return inv;
    }

    @Override
    public void cancelInvite(Long teamId, Long invitationId, String actorEmail) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException("Team not found", 404));
        User actor = userRepository.findByEmail(actorEmail).orElseThrow(() -> new CustomException("User not found", 404));

        enforceCanManageTeam(actor, team);

        TeamInvitation inv = teamInvitationRepository.findByIdAndTeamId(invitationId, teamId)
                .orElseThrow(() -> new CustomException("Invitation not found", 404));

        if (inv.isAccepted()) throw new CustomException("Invitation already accepted", 409);

        teamInvitationRepository.delete(inv);
    }

    @Override
    public List<TeamInvitation> listPendingInvites(Long teamId, String actorEmail) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException("Team not found", 404));
        User actor = userRepository.findByEmail(actorEmail).orElseThrow(() -> new CustomException("User not found", 404));

        enforceCanManageTeam(actor, team);

        return teamInvitationRepository.findPendingByTeamId(teamId);
    }

    @Override
    public void acceptInvite(String token, String actorEmail) {
        if (token == null || token.isBlank()) throw new CustomException("Token required", 400);

        TeamInvitation inv = teamInvitationRepository.findByToken(token)
                .orElseThrow(() -> new CustomException("Invalid token", 404));

        if (inv.isAccepted()) throw new CustomException("Invitation already accepted", 409);

        User actor = userRepository.findByEmail(actorEmail)
                .orElseThrow(() -> new CustomException("User not found", 404));

        if (!actor.getEmail().equalsIgnoreCase(inv.getInvitedEmail()))
            throw new CustomException("Token does not match your account email", 403);

        addMemberToTeam(actor, inv.getTeam());
        inv.setAccepted(true);
        teamInvitationRepository.save(inv);
    }

    private void enforceCanManageTeam(User actor, Team team) {
        if (!actor.isEmailVerified()) throw new CustomException("User not verified", 403);

        boolean owner = team.getOwner() != null && team.getOwner().getId().equals(actor.getId());
        boolean isAdmin = actor.getRoles() != null &&
                actor.getRoles().stream().anyMatch(r -> "ADMIN".equalsIgnoreCase(r.getName()));

        if (!(owner || isAdmin)) throw new CustomException("Forbidden", 403);
    }

    private void addMemberToTeam(User user, Team team) {
        if (team.getMembers() == null) {
            team.setMembers(new HashSet<>());
        }

        if (team.getMembers().stream().anyMatch(u -> u.getId().equals(user.getId()))) return;

        team.getMembers().add(user);
        teamRepository.save(team);
    }
}
