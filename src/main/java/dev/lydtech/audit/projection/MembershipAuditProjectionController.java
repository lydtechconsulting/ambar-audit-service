package dev.lydtech.audit.projection;

import dev.lydtech.audit.api.AmbarHttpRequest;
import dev.lydtech.audit.api.Event;
import dev.lydtech.audit.serializer.Deserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/audit/cooking-club/membership/projection")
public class MembershipAuditProjectionController {
    @Autowired
    private final MembershipAuditProjectionHandler membershipAuditProjectionHandler;

    @Autowired
    private final Deserializer deserializer;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String projectMembershipAudit(@RequestBody AmbarHttpRequest request) {
        try {
            Event event = deserializer.deserialize(request.getSerializedEvent());
            membershipAuditProjectionHandler.project(request.getSerializedEvent().getAggregateId(), event);
            return "{\"result\":{\"success\":{}}}";
        } catch(Exception e) {
            log.error(e.getMessage());
            return "{\"result\":{\"success\":{}}}";
        }
    }
}
