package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;

public record Email(@NonNull String value) {
    public Email {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(value)) {
            throw new IllegalArgumentException("Email is invalid.");
        }
    }
}
