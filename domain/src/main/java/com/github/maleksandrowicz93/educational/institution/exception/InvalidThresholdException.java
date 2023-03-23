package com.github.maleksandrowicz93.educational.institution.exception;

import com.github.maleksandrowicz93.educational.institution.common.DomainException;

public class InvalidThresholdException extends DomainException {

    public InvalidThresholdException() {
        super("Critical point cannot be negative.");
    }
}
