package com.souunit.gohabit.model;

public enum MetaStatus {
    COMPLETED ("completed"),
    NOT_STARTED("not_started"),
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
