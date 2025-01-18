package dev.lydtech.audit.controller;

import dev.lydtech.audit.api.MembershipStatusResponse;
import dev.lydtech.audit.projection.MembershipAuditProjectionHandler;
import dev.lydtech.audit.query.MembershipAuditQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit/cooking-club/membership/query")
@RequiredArgsConstructor
public class MembershipAuditController {

    private final MembershipAuditQueryHandler membershipAuditQueryHandler;

    @GetMapping("/list")
    public ResponseEntity<List<MembershipStatusResponse>> listMembers() {
        List<MembershipStatusResponse> memberStatusResponses = membershipAuditQueryHandler.getAllMembershipStatus();
        return ResponseEntity.ok(memberStatusResponses);
    }
}
