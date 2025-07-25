package com.codmtracker.repository;

import com.codmtracker.model.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByTeamId(Long teamId);

    long countByTeamId(Long teamId);

    List<Game> findByTeamIdOrderByPlayedAtDesc(Long teamId, Pageable pageable);
}
