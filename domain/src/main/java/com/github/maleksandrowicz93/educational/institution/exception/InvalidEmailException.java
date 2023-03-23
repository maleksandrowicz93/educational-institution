package com.github.maleksandrowicz93.educational.institution.exception;

import com.github.maleksandrowicz93.educational.institution.common.DomainException;

public class InvalidEmailException extends DomainException {

    public InvalidEmailException() {
        super("Invalid value.");
    }
}
