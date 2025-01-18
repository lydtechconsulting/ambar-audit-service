package dev.lydtech.audit.repository;

import java.util.Optional;
import java.util.UUID;

import dev.lydtech.audit.domain.MembershipAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipAuditRepository extends JpaRepository<MembershipAudit, UUID> {
    Optional<MembershipAudit> findByAggregateId(String aggregateId);
}
