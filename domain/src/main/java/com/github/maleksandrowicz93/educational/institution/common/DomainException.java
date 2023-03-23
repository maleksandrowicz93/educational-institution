package com.github.maleksandrowicz93.educational.institution.common;

public abstract class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
