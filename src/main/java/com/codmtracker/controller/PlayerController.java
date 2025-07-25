package com.codmtracker.controller;

import com.codmtracker.dto.PlayerDto;
import com.codmtracker.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public PlayerDto createPlayer(@RequestBody PlayerDto dto) {
        return playerService.createPlayer(dto);
    }

    @GetMapping("/{id}")
    public PlayerDto getPlayerById(@PathVariable Long id) {
        return playerService.getPlayerById(id);
    }

    @GetMapping("/team/{teamId}")
    public List<PlayerDto> getPlayersByTeam(@PathVariable Long teamId) {
        return playerService.getPlayersByTeamId(teamId);
    }

    @PatchMapping("/{id}/deactivate")
    public void deactivatePlayer(@PathVariable Long id) {
        playerService.deactivatePlayer(id);
    }
}
