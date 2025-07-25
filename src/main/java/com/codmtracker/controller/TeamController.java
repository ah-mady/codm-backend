package com.codmtracker.controller;

import com.codmtracker.dto.TeamDto;
import com.codmtracker.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public TeamDto createTeam(@RequestBody TeamDto dto, @RequestParam Long ownerId) {
        return teamService.createTeam(dto, ownerId);
    }

    @GetMapping("/{id}")
    public TeamDto getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    @GetMapping("/user/{userId}")
    public List<TeamDto> getTeamsByUser(@PathVariable Long userId) {
        return teamService.getTeamsByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable Long id, @RequestParam Long ownerId) {
        teamService.deleteTeam(id, ownerId);
    }

    @PatchMapping("/{id}/base-kill")
    public void updateBaseKill(@PathVariable Long id, @RequestParam int baseKill, @RequestParam Long adminUserId) {
        teamService.updateBaseKill(id, baseKill, adminUserId);
    }
}
