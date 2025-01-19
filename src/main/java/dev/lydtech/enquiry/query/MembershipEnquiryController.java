package dev.lydtech.enquiry.controller;

import dev.lydtech.enquiry.api.MembershipResponse;
import dev.lydtech.enquiry.query.MembershipEnquiryQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enquiry/cooking-club/membership/query")
@RequiredArgsConstructor
public class MembershipEnquiryController {

    private final MembershipEnquiryQueryHandler membershipEnquiryQueryHandler;

    @GetMapping("/list")
    public ResponseEntity<List<MembershipResponse>> listMemberships() {
        List<MembershipResponse> membershipResponses = membershipEnquiryQueryHandler.getAllMemberships();
        return ResponseEntity.ok(membershipResponses);
    }
}
