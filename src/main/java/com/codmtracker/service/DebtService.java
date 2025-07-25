package com.codmtracker.service;

import com.codmtracker.dto.DebtDto;

import java.util.List;

public interface DebtService {
    DebtDto getDebtByPlayerId(Long playerId);

    List<DebtDto> getDebtsByTeamId(Long teamId);

    void updateDebt(Long playerId, int newDebtAmount);
}
