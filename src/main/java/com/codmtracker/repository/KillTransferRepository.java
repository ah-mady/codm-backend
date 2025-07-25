package com.codmtracker.repository;

import com.codmtracker.model.KillTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KillTransferRepository extends JpaRepository<KillTransfer, Long> {
    List<KillTransfer> findByGameId(Long gameId);

    List<KillTransfer> findByFromPlayerId(Long playerId);

    List<KillTransfer> findByToPlayerId(Long playerId);
}
