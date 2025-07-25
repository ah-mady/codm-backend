package com.codmtracker.service;

import com.codmtracker.dto.PlayerDto;
import com.codmtracker.exception.CustomException;
import com.codmtracker.model.Player;
import com.codmtracker.model.Team;
import com.codmtracker.model.User;
import com.codmtracker.repository.PlayerRepository;
import com.codmtracker.repository.TeamRepository;
import com.codmtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public PlayerDto createPlayer(PlayerDto dto) {
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new CustomException("Team not found", 404));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User not found", 404));
        Player player = Player.builder()
                .codmName(dto.getCodmName())
                .roleType(dto.getRoleType())
                .active(dto.isActive())
                .team(team)
                .user(user)
                .build();
        playerRepository.save(player);
        return PlayerDto.builder()
                .id(player.getId())
                .codmName(player.getCodmName())
                .roleType(player.getRoleType())
                .active(player.isActive())
                .teamId(team.getId())
                .userId(user.getId())
                .build();
    }

    @Override
    public PlayerDto getPlayerById(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new CustomException("Player not found", 404));
        return PlayerDto.builder()
                .id(player.getId())
                .codmName(player.getCodmName())
                .roleType(player.getRoleType())
                .active(player.isActive())
                .teamId(player.getTeam().getId())
                .userId(player.getUser().getId())
                .build();
    }

    @Override
    public List<PlayerDto> getPlayersByTeamId(Long teamId) {
        return playerRepository.findByTeamId(teamId).stream().map(
                p -> PlayerDto.builder()
                        .id(p.getId())
                        .codmName(p.getCodmName())
                        .roleType(p.getRoleType())
                        .active(p.isActive())
                        .teamId(p.getTeam().getId())
                        .userId(p.getUser().getId())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public void deactivatePlayer(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow();
        player.setActive(false);
        playerRepository.save(player);
    }
}
