package dev.lydtech.enquiry.api;

import dev.lydtech.enquiry.domain.MembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApplicationEvaluated extends Event {
    private MembershipStatus evaluationOutcome;
}
