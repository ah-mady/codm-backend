package com.codmtracker.repository;

import com.codmtracker.model.MapInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapInfoRepository extends JpaRepository<MapInfo, Long> {
    List<MapInfo> findByTeamId(Long teamId);
}
