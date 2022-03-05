package com.project.interviewchallenge.user.response;

import lombok.Getter;

public enum ResponseCode {

    SUCCESS("0"), FAILED("99");

    @Getter
    private String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.name().replace('_', '.').toLowerCase();
    }

}
