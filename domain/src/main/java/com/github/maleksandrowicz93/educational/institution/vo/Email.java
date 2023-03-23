package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.exception.InvalidEmailException;
import org.apache.commons.validator.routines.EmailValidator;

public record Email(String value) {
    public Email {
        var validEmail = EmailValidator.getInstance().isValid(value);
        if (!validEmail) {
            throw new InvalidEmailException();
        }
    }
}
