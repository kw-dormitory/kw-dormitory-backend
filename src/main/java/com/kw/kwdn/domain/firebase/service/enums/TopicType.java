package com.kw.kwdn.domain.firebase.service.enums;

public enum TopicType {
    NOTICE("NOTICE"), CURFEW("CURFEW"), REGULAR_RECRUITMENT("REGULAR_RECRUITMENT");
    private final String value;

    public String value() {
        return value;
    }

    TopicType(String value) {
        this.value = value;
    }
}
