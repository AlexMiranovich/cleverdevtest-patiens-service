package com.cleverdev.patientservice.entities.dto;

public enum ClientStatusEnum {

    PENDING(210),
    ACTIVE(200),
    INACTIVE(230);

    private final Integer code;

    ClientStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
