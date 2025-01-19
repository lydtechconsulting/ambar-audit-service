package dev.lydtech.enquiry.query;

import dev.lydtech.enquiry.api.MembershipResponse;
import dev.lydtech.enquiry.domain.Membership;
import dev.lydtech.enquiry.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipEnquiryQueryHandler {

    private final MembershipRepository membershipRepository;

    public List<MembershipResponse> getAllMemberships() {
        List<Membership> memberships = membershipRepository.findAll();
        return memberships.stream().map(membership -> MembershipResponse.builder()
                        .name(membership.getName())
                        .status(membership.getMembershipStatus().name())
                        .createdAt(membership.getCreatedAt().toString())
                        .lastUpdatedAt(membership.getLastUpdatedAt().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
