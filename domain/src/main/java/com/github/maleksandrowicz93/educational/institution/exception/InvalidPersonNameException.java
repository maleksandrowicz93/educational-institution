package com.github.maleksandrowicz93.educational.institution.exception;

import com.github.maleksandrowicz93.educational.institution.common.DomainException;

public class InvalidPersonNameException extends DomainException {

    public InvalidPersonNameException() {
        super("Firstname and Lastname cannot be blank.");
    }
}
