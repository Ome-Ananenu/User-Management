package com.project.interviewchallenge.user.exceptions;

import lombok.Getter;

public class ApplicationDomainException extends RuntimeException {

    @Getter
    private String[] messageArgs;

    public ApplicationDomainException() {
        super();
    }

    public ApplicationDomainException(String message, String[] messageArgs) {
        super(message);
        this.messageArgs = messageArgs;
    }

    public ApplicationDomainException(String message, String[] messageArgs, Throwable t) {
        super(message, t);
        this.messageArgs = messageArgs;
    }

    public ApplicationDomainException(Throwable t) {
        super(t);
    }
}