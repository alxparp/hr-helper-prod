package com.hrsol.helper.entity;

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
