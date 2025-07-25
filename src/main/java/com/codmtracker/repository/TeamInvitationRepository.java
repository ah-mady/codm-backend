package com.codmtracker.repository;

import com.codmtracker.model.TeamInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamInvitationRepository extends JpaRepository<TeamInvitation, Long> {
    Optional<TeamInvitation> findByToken(String token);

    List<TeamInvitation> findByTeamIdAndAcceptedFalse(Long teamId);

    List<TeamInvitation> findByInvitedEmailAndAcceptedFalse(String invitedEmail);

    Optional<TeamInvitation> findByIdAndTeamId(Long id, Long teamId);

    default List<TeamInvitation> findPendingByTeamId(Long teamId) {
        return findByTeamIdAndAcceptedFalse(teamId);
    }
}
