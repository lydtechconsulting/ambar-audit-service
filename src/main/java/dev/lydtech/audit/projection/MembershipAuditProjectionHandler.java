package dev.lydtech.audit.projection;

import java.time.Instant;
import java.util.Optional;

import dev.lydtech.audit.api.ApplicationEvaluated;
import dev.lydtech.audit.api.ApplicationSubmitted;
import dev.lydtech.audit.api.Event;
import dev.lydtech.audit.domain.MembershipAudit;
import dev.lydtech.audit.repository.MembershipAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import static dev.lydtech.audit.domain.MembershipStatus.Requested;

@Slf4j
@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class MembershipAuditProjectionHandler {
    private final MembershipAuditRepository membershipAuditRepository;

    public void project(String aggregateId, Event event) {
        if (event instanceof ApplicationSubmitted applicationSubmitted) {
            projectApplicationSubmitted(aggregateId, applicationSubmitted);
        } else if(event instanceof ApplicationEvaluated applicationEvaluated) {
            projectApplicationEvaluated(aggregateId, applicationEvaluated);
        }
    }

    private void projectApplicationSubmitted(String aggregateId, ApplicationSubmitted applicationSubmitted) {
        String applicantName = applicationSubmitted.getFirstName() + " " + applicationSubmitted.getLastName();
        Optional<MembershipAudit> existingAudit = membershipAuditRepository.findByAggregateId(aggregateId);
        if (existingAudit.isPresent()) {
            log.info("Application submitted for '" + applicantName + "' already audited for aggregate id + '" + aggregateId + "'.  Ignoring event.");
            return;
        }
        Instant now = Instant.now();
        MembershipAudit newAudit = MembershipAudit.builder()
                .aggregateId(aggregateId)
                .name(applicantName)
                .membershipStatus(Requested)
                .createdAt(now)
                .lastUpdatedAt(now)
                .build();
        membershipAuditRepository.save(newAudit);
        log.info("Application submitted: membership for '" + applicantName + "' audited for aggregate id + '" + aggregateId);
    }

    private void projectApplicationEvaluated(String aggregateId, ApplicationEvaluated applicationEvaluated) {
        Optional<MembershipAudit> existingAudit = membershipAuditRepository.findByAggregateId(aggregateId);
        if (existingAudit.isPresent()) {
            MembershipAudit audit = existingAudit.get();
            if(audit.getMembershipStatus() != applicationEvaluated.getEvaluationOutcome()) {
                audit.setMembershipStatus(applicationEvaluated.getEvaluationOutcome());
                audit.setLastUpdatedAt(Instant.now());
                membershipAuditRepository.save(audit);
                log.info("Application evaluated: membership for '" + existingAudit.get().getName() + "' with aggregateId + '" + aggregateId + "' updated with status '" + applicationEvaluated.getEvaluationOutcome() + "'.");
            } else {
                log.info("Application evaluated: membership for '" + existingAudit.get().getName() + "' with aggregateId + '" + aggregateId + "' not updated as status '" + applicationEvaluated.getEvaluationOutcome() + "' has not changed.");
            }
        } else {
            String errorMessage = "Application evaluated: no membership audit found for aggregate id '" + aggregateId + "'.";
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }
}
