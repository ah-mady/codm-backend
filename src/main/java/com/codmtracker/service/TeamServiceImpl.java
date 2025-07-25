package com.codmtracker.service;

import com.codmtracker.dto.TeamDto;
import com.codmtracker.model.Team;
import com.codmtracker.model.User;
import com.codmtracker.repository.TeamRepository;
import com.codmtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public TeamDto createTeam(TeamDto dto, Long ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow();
        Team team = Team.builder()
                .name(dto.getName())
                .createdAt(Instant.now())
                .owner(owner)
                .build();
        teamRepository.save(team);
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .createdAt(team.getCreatedAt())
                .ownerId(owner.getId())
                .build();
    }

    @Override
    public TeamDto getTeamById(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow();
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .createdAt(team.getCreatedAt())
                .ownerId(team.getOwner().getId())
                .build();
    }

    @Override
    public List<TeamDto> getTeamsByUserId(Long userId) {
        return teamRepository.findByOwnerId(userId).stream()
                .map(team -> TeamDto.builder()
                        .id(team.getId())
                        .name(team.getName())
                        .createdAt(team.getCreatedAt())
                        .ownerId(team.getOwner().getId())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    public void deleteTeam(Long teamId, Long ownerId) {
        Team team = teamRepository.findByIdAndOwnerId(teamId, ownerId).orElseThrow();
        teamRepository.delete(team);
    }

    @Override
    public void updateBaseKill(Long teamId, int newBaseKill, Long adminUserId) {
        com.codmtracker.model.Team team = teamRepository.findById(teamId).orElseThrow();
        if (!team.getOwner().getId().equals(adminUserId))
            throw new RuntimeException("Only the team admin can update base kill.");
        team.setBaseKill(newBaseKill);
        teamRepository.save(team);
    }

}
