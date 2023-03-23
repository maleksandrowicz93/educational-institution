package com.github.maleksandrowicz93.educational.institution.exception;

import com.github.maleksandrowicz93.educational.institution.common.DomainException;

public class InvalidIdentificationNumberException extends DomainException {

    public InvalidIdentificationNumberException() {
        super("National identification value cannot be blank.");
    }
}
