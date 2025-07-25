package com.codmtracker.service;

import com.codmtracker.dto.GameDto;
import com.codmtracker.dto.PlayerDto;

import java.util.List;
import java.util.Map;

public interface StatsService {
    Map<String, Object> getTeamStats(Long teamId);

    Map<String, Object> getPlayerStats(Long playerId);

    List<GameDto> getRecentGames(Long teamId, int limit);

    List<PlayerDto> getLeaderboard(Long teamId, String type);
}
