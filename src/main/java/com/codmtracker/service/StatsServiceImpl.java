package com.codmtracker.service;

import com.codmtracker.dto.GameDto;
import com.codmtracker.dto.KillDto;
import com.codmtracker.dto.PlayerDto;
import com.codmtracker.model.Game;
import com.codmtracker.model.Player;
import com.codmtracker.repository.GameRepository;
import com.codmtracker.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Map<String, Object> getTeamStats(Long teamId) {
        Map<String, Object> stats = new HashMap<>();
        long games = gameRepository.countByTeamId(teamId);
        stats.put("gamesPlayed", games);
        return stats;
    }

    @Override
    public Map<String, Object> getPlayerStats(Long playerId) {
        Map<String, Object> stats = new HashMap<>();
        Player p = playerRepository.findById(playerId).orElseThrow();
        stats.put("codmName", p.getCodmName());
        stats.put("team", p.getTeam().getName());
        return stats;
    }

    @Override
    public List<GameDto> getRecentGames(Long teamId, int limit) {
        var pageable = PageRequest.of(0, limit);
        List<Game> games = gameRepository.findByTeamIdOrderByPlayedAtDesc(teamId, pageable);
        List<GameDto> res = new ArrayList<>();
        for (Game g : games)
            res.add(GameDto.builder()
                    .id(g.getId())
                    .map(g.getMap())
                    .victory(g.isVictory())
                    .mode(g.getMode())
                    .playedAt(g.getPlayedAt())
                    .mvpPlayerName(g.getMvpPlayerName())
                    .teamId(g.getTeam().getId())
                    .participants(
                            g.getParticipants().stream()
                                    .map(part -> KillDto.builder()
                                            .playerId(part.getPlayer().getId())
                                            .kills(part.getKills())
                                            .mvp(part.isMvp())
                                            .build())
                                    .collect(Collectors.toList()))
                    .build());
        return res;
    }

    @Override
    public List<PlayerDto> getLeaderboard(Long teamId, String type) {
        List<Player> players = playerRepository.findByTeamId(teamId);
        List<PlayerDto> res = new ArrayList<>();
        for (Player p : players) res.add(PlayerDto.builder().id(p.getId()).codmName(p.getCodmName()).build());
        return res;
    }
}
