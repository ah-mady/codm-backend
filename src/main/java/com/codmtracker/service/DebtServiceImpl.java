package com.codmtracker.service;

import com.codmtracker.dto.DebtDto;
import com.codmtracker.exception.CustomException;
import com.codmtracker.model.Debt;
import com.codmtracker.repository.DebtRepository;
import com.codmtracker.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtServiceImpl implements DebtService {

    @Autowired
    private DebtRepository debtRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public DebtDto getDebtByPlayerId(Long playerId) {
        Debt d = debtRepository.findByPlayerId(playerId)
                .orElseThrow(() -> new CustomException("Debt not found", 404));
        return DebtDto.builder()
                .id(d.getId())
                .playerId(playerId)
                .debtAmount(d.getDebtAmount())
                .lastUpdated(d.getLastUpdated())
                .build();
    }

    @Override
    public List<DebtDto> getDebtsByTeamId(Long teamId) {
        List<Debt> debts = debtRepository.findByTeamId(teamId);
        if (debts == null || debts.isEmpty())
            throw new CustomException("No debts found for team", 404);
        return debtRepository.findByTeamId(teamId)
                .stream()
                .map(d -> DebtDto.builder()
                        .id(d.getId())
                        .playerId(d.getPlayer().getId())
                        .debtAmount(d.getDebtAmount())
                        .lastUpdated(d.getLastUpdated())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void updateDebt(Long playerId, int newDebtAmount) {
        Debt d = debtRepository.findByPlayerId(playerId).orElseThrow();
        d.setDebtAmount(newDebtAmount);
        debtRepository.save(d);
    }
}
