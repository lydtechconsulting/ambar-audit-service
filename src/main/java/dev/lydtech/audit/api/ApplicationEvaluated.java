package dev.lydtech.audit.api;

import dev.lydtech.audit.domain.MembershipStatus;
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
