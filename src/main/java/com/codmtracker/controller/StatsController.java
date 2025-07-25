package com.codmtracker.controller;

import com.codmtracker.dto.GameDto;
import com.codmtracker.dto.PlayerDto;
import com.codmtracker.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/team/{teamId}")
    public Map<String, Object> getTeamStats(@PathVariable Long teamId) {
        return statsService.getTeamStats(teamId);
    }

    @GetMapping("/player/{playerId}")
    public Map<String, Object> getPlayerStats(@PathVariable Long playerId) {
        return statsService.getPlayerStats(playerId);
    }

    @GetMapping("/recent/{teamId}")
    public List<GameDto> getRecentGames(@PathVariable Long teamId, @RequestParam int limit) {
        return statsService.getRecentGames(teamId, limit);
    }

    @GetMapping("/leaderboard/{teamId}")
    public List<PlayerDto> getLeaderboard(@PathVariable Long teamId, @RequestParam String type) {
        return statsService.getLeaderboard(teamId, type);
    }
}
