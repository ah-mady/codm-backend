package com.codmtracker.repository;

import com.codmtracker.model.GameParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameParticipantRepository extends JpaRepository<GameParticipant, Long> {
    List<GameParticipant> findByGameId(Long gameId);

    List<GameParticipant> findByPlayerId(Long playerId);
}
