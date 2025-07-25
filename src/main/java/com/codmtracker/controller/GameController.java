package com.codmtracker.controller;

import com.codmtracker.dto.GameDto;
import com.codmtracker.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping
    public GameDto createGame(@RequestBody GameDto dto) {
        return gameService.createGame(dto);
    }

    @GetMapping("/{id}")
    public GameDto getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }

    @GetMapping("/team/{teamId}")
    public List<GameDto> getGamesByTeam(@PathVariable Long teamId) {
        return gameService.getGamesByTeamId(teamId);
    }
}
