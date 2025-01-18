package dev.lydtech.audit.serializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lydtech.audit.api.ApplicationEvaluated;
import dev.lydtech.audit.api.ApplicationSubmitted;
import dev.lydtech.audit.api.Event;
import dev.lydtech.audit.api.SerializedEvent;
import dev.lydtech.audit.domain.MembershipStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Deserializer {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public Event deserialize(SerializedEvent serializedEvent) {
        try {
            JsonNode jsonPayload = objectMapper.readTree(serializedEvent.getJsonPayload());
            if ("CookingClub_Membership_ApplicationSubmitted".equals(serializedEvent.getEventName())) {
                return ApplicationSubmitted.builder()
                        .firstName(payloadString(jsonPayload, "firstName"))
                        .lastName(payloadString(jsonPayload, "lastName"))
                        .build();
            } else if ("CookingClub_Membership_ApplicationEvaluated".equals(serializedEvent.getEventName())) {
                return ApplicationEvaluated.builder()
                        .evaluationOutcome(MembershipStatus.fromString(payloadString(jsonPayload, "evaluationOutcome")))
                        .build();
            }
            throw new RuntimeException("Unknown event type: " + serializedEvent.getEventName());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String payloadString(JsonNode node, String fieldName) {
        try {
            JsonNode value = node.get(fieldName);
            if (value == null) {
                throw new IllegalArgumentException("Required field " + fieldName + " is missing");
            }
            return value.asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read field: " + fieldName, e);
        }
    }
}
