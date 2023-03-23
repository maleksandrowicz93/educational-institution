package com.github.maleksandrowicz93.educational.institution.exception;

import com.github.maleksandrowicz93.educational.institution.common.DomainException;

public class InvalidPhoneNumberException extends DomainException {

    public InvalidPhoneNumberException() {
        super("Phone number cannot be blank");
    }
}
