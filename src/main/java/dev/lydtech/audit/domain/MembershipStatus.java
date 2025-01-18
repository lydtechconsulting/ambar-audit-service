package dev.lydtech.audit.domain;

public enum MembershipStatus {

    Requested,
    Approved,
    Rejected;

    public static MembershipStatus fromString(String status) {
        try {
            return MembershipStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid MembershipStatus: " + status);
        }
    }
}
