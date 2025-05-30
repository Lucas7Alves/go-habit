package com.souunit.gohabit.model;

public enum MetaStatus {
    COMPLETED ("completed"),
    NOT_COMPLETED("not_completed"),
    PENDING ("pending");

    private final String value;

    MetaStatus(String value) {
        this.value = value;
    }

    public String getValue() { return value; }

    public static MetaStatus fromValue(String value) {
        for (MetaStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
