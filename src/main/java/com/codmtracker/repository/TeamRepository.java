package com.codmtracker.repository;

import com.codmtracker.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByOwnerId(Long ownerId);

    Optional<Team> findByIdAndOwnerId(Long teamId, Long ownerId);
}
