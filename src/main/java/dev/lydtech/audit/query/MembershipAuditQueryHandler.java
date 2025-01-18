package dev.lydtech.audit.query;

import dev.lydtech.audit.api.MembershipStatusResponse;
import dev.lydtech.audit.domain.MembershipAudit;
import dev.lydtech.audit.repository.MembershipAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipAuditQueryHandler {

    private final MembershipAuditRepository membershipAuditRepository;

    public List<MembershipStatusResponse> getAllMembershipStatus() {
        List<MembershipAudit> audits = membershipAuditRepository.findAll();
        return audits.stream().map(audit -> MembershipStatusResponse.builder()
                        .name(audit.getName())
                        .status(audit.getMembershipStatus().name())
                        .createdAt(audit.getCreatedAt().toString())
                        .lastUpdatedAt(audit.getLastUpdatedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
