package dev.lydtech.audit.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipAudit implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String aggregateId;

    private String name;

    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;

    private Instant createdAt;

    private Instant lastUpdatedAt;
}
