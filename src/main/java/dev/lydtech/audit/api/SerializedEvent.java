package dev.lydtech.audit.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SerializedEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("aggregate_id")
    private String aggregateId;

    @JsonProperty("causation_id")
    private String causationId;

    @JsonProperty("correlation_id")
    private String correlationId;

    @JsonProperty("aggregate_version")
    private Integer aggregateVersion;

    @JsonProperty("json_payload")
    private String jsonPayload;

    @JsonProperty("json_metadata")
    private String jsonMetadata;

    @JsonProperty("recorded_on")
    private String recordedOn;

    @JsonProperty("event_name")
    private String eventName;
}
