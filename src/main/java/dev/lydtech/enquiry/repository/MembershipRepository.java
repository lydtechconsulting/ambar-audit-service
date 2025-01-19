package dev.lydtech.enquiry.repository;

import java.util.Optional;
import java.util.UUID;

import dev.lydtech.enquiry.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    Optional<Membership> findByAggregateId(String aggregateId);
}
