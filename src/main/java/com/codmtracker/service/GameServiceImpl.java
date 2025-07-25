package com.codmtracker.service;

import com.codmtracker.dto.GameDto;
import com.codmtracker.dto.KillDto;
import com.codmtracker.exception.CustomException;
import com.codmtracker.model.Game;
import com.codmtracker.model.GameParticipant;
import com.codmtracker.model.Player;
import com.codmtracker.model.Team;
import com.codmtracker.repository.GameRepository;
import com.codmtracker.repository.PlayerRepository;
import com.codmtracker.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public GameDto createGame(GameDto dto) {
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();
        Game game = Game.builder()
                .map(dto.getMap())
                .victory(dto.isVictory())
                .playedAt(dto.getPlayedAt())
                .mode(dto.getMode())
                .team(team)
                .mvpPlayerName(dto.getMvpPlayerName())
                .build();
        List<GameParticipant> parts = dto.getParticipants().stream().map(kd -> {
            Player p = playerRepository.findById(kd.getPlayerId()).orElseThrow();
            return GameParticipant.builder()
                    .game(game)
                    .player(p)
                    .kills(kd.getKills())
                    .mvp(kd.isMvp())
                    .build();
        }).collect(Collectors.toList());
        game.setParticipants(parts);
        gameRepository.save(game);
        return dto;
    }

    @Override
    public GameDto getGameById(Long gameId) {
        Game g = gameRepository.findById(gameId)
                .orElseThrow(() -> new CustomException("Game not found", 404));
        List<KillDto> participants = g.getParticipants().stream().map(
                part -> KillDto.builder()
                        .playerId(part.getPlayer().getId())
                        .kills(part.getKills())
                        .mvp(part.isMvp())
                        .build()).collect(Collectors.toList());
        return GameDto.builder()
                .id(g.getId())
                .map(g.getMap())
                .victory(g.isVictory())
                .mode(g.getMode())
                .playedAt(g.getPlayedAt())
                .mvpPlayerName(g.getMvpPlayerName())
                .teamId(g.getTeam().getId())
                .participants(participants)
                .build();
    }

    @Override
    public List<GameDto> getGamesByTeamId(Long teamId) {
        return gameRepository.findByTeamId(teamId).stream().map(g -> {
            List<KillDto> participants = g.getParticipants().stream().map(
                    part -> KillDto.builder()
                            .playerId(part.getPlayer().getId())
                            .kills(part.getKills())
                            .mvp(part.isMvp())
                            .build()).collect(Collectors.toList());
            return GameDto.builder()
                    .id(g.getId())
                    .map(g.getMap())
                    .victory(g.isVictory())
                    .mode(g.getMode())
                    .playedAt(g.getPlayedAt())
                    .mvpPlayerName(g.getMvpPlayerName())
                    .teamId(g.getTeam().getId())
                    .participants(participants)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }
}
