package com.codmtracker.repository;

import com.codmtracker.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeamId(Long teamId);

    List<Player> findByUserId(Long userId);
}
