package com.github.maleksandrowicz93.educational.institution.exception;

import com.github.maleksandrowicz93.educational.institution.common.DomainException;

public class InvalidYearsOfExperienceException extends DomainException {

    public InvalidYearsOfExperienceException() {
        super("Years of experience amount cannot be negative.");
    }
}
