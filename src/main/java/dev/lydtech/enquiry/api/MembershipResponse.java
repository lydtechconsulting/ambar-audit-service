package dev.lydtech.enquiry.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipResponse {
    private String name;
    private String status;
    private String createdAt;
    private String lastUpdatedAt;
}

