package dev.lydtech.audit.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipStatusResponse {
    private String name;
    private String status;
    private String createdAt;
    private String lastUpdatedAt;
}

