package com.cleverdev.patientservice.entities.dto;

public enum ClientStatusEnum {

    ACTIVE(200),
    PENDING(210),
    INACTIVE(230);

    private final Integer code;

    ClientStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
