package com.codmtracker.service;

import com.codmtracker.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    PlayerDto createPlayer(PlayerDto playerDto);

    PlayerDto getPlayerById(Long playerId);

    List<PlayerDto> getPlayersByTeamId(Long teamId);

    void deactivatePlayer(Long playerId);
}
