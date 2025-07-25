package com.codmtracker.service;

import com.codmtracker.dto.TeamDto;

import java.util.List;

public interface TeamService {
    TeamDto createTeam(TeamDto teamDto, Long ownerId);

    TeamDto getTeamById(Long teamId);

    List<TeamDto> getTeamsByUserId(Long userId);

    void deleteTeam(Long teamId, Long ownerId);

    void updateBaseKill(Long teamId, int newBaseKill, Long adminUserId);

}
