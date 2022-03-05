package com.project.interviewchallenge.user.exceptions;

public class BadRequestException extends ApplicationDomainException{

    public BadRequestException(String message, String... messageArgs) {
        super(message, messageArgs);
    }

    public BadRequestException(Throwable t, String message, String... messageArgs) {
        super(message, messageArgs, t);
    }

}
