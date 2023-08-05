package com.hrsol.helper.entity.enums;

public enum UserStatusType {

    AVAILABLE ("available"),
    NOT_AVAILABLE("not available");

    private final String value;

    UserStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
