package dev.lydtech.enquiry.projection;

import java.time.Instant;
import java.util.Optional;

import dev.lydtech.enquiry.api.ApplicationEvaluated;
import dev.lydtech.enquiry.api.ApplicationSubmitted;
import dev.lydtech.enquiry.api.Event;
import dev.lydtech.enquiry.domain.Membership;
import dev.lydtech.enquiry.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import static dev.lydtech.enquiry.domain.MembershipStatus.Requested;

@Slf4j
@Service
@RequestScope
@RequiredArgsConstructor
@Transactional
public class MembershipProjectionHandler {
    private final MembershipRepository membershipRepository;

    public void project(String aggregateId, Event event) {
        if (event instanceof ApplicationSubmitted applicationSubmitted) {
            projectApplicationSubmitted(aggregateId, applicationSubmitted);
        } else if(event instanceof ApplicationEvaluated applicationEvaluated) {
            projectApplicationEvaluated(aggregateId, applicationEvaluated);
        }
    }

    private void projectApplicationSubmitted(String aggregateId, ApplicationSubmitted applicationSubmitted) {
        String applicantName = applicationSubmitted.getFirstName() + " " + applicationSubmitted.getLastName();
        Optional<Membership> existingAudit = membershipRepository.findByAggregateId(aggregateId);
        if (existingAudit.isPresent()) {
            log.info("Application submitted for '" + applicantName + "' already persisted for aggregate id + '" + aggregateId + "'.  Ignoring event.");
            return;
        }
        Instant now = Instant.now();
        Membership newMembershipStatus = Membership.builder()
                .aggregateId(aggregateId)
                .name(applicantName)
                .membershipStatus(Requested)
                .createdAt(now)
                .lastUpdatedAt(now)
                .build();
        membershipRepository.save(newMembershipStatus);
        log.info("Application submitted: membership for '" + applicantName + "' persisted for aggregate id + '" + aggregateId);
    }

    private void projectApplicationEvaluated(String aggregateId, ApplicationEvaluated applicationEvaluated) {
        Optional<Membership> existingMembershipStatus = membershipRepository.findByAggregateId(aggregateId);
        if (existingMembershipStatus.isPresent()) {
            Membership membership = existingMembershipStatus.get();
            if(membership.getMembershipStatus() != applicationEvaluated.getEvaluationOutcome()) {
                membership.setMembershipStatus(applicationEvaluated.getEvaluationOutcome());
                membership.setLastUpdatedAt(Instant.now());
                membershipRepository.save(membership);
                log.info("Application evaluated: membership for '" + existingMembershipStatus.get().getName() + "' with aggregateId + '" + aggregateId + "' updated with status '" + applicationEvaluated.getEvaluationOutcome() + "'.");
            } else {
                log.info("Application evaluated: membership for '" + existingMembershipStatus.get().getName() + "' with aggregateId + '" + aggregateId + "' not updated as status '" + applicationEvaluated.getEvaluationOutcome() + "' has not changed.");
            }
        } else {
            String errorMessage = "Application evaluated: no membership found for aggregate id '" + aggregateId + "'.";
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }
}
