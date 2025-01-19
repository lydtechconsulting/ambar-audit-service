package dev.lydtech.enquiry.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApplicationSubmitted extends Event {
    private String firstName;
    private String lastName;
}
