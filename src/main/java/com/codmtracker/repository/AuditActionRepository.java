package com.codmtracker.repository;

import com.codmtracker.model.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditActionRepository extends JpaRepository<AuditAction, Long> {
}
