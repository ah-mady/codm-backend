package com.codmtracker.service;

import com.codmtracker.dto.GameDto;

import java.util.List;

public interface GameService {
    GameDto createGame(GameDto gameDto);

    GameDto getGameById(Long gameId);

    List<GameDto> getGamesByTeamId(Long teamId);

    void deleteGame(Long gameId);
}
