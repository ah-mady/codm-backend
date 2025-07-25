package com.codmtracker.repository;

import com.codmtracker.model.PlayerMapStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerMapStatRepository extends JpaRepository<PlayerMapStat, Long> {
    List<PlayerMapStat> findByPlayerId(Long playerId);

    List<PlayerMapStat> findByMapId(Long mapId);
}
