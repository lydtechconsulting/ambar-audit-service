package dev.lydtech.enquiry.projection;

import dev.lydtech.enquiry.api.AmbarHttpRequest;
import dev.lydtech.enquiry.api.Event;
import dev.lydtech.enquiry.serializer.Deserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/enquiry/cooking-club/membership/projection")
public class MembershipProjectionController {
    @Autowired
    private final MembershipProjectionHandler membershipProjectionHandler;

    @Autowired
    private final Deserializer deserializer;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String projectMembership(@RequestBody AmbarHttpRequest request) {
        try {
            Event event = deserializer.deserialize(request.getSerializedEvent());
            membershipProjectionHandler.project(request.getSerializedEvent().getAggregateId(), event);
            return "{\"result\":{\"success\":{}}}";
        } catch(Exception e) {
            log.error(e.getMessage());
            return "{\"result\":{\"success\":{}}}";
        }
    }
}
