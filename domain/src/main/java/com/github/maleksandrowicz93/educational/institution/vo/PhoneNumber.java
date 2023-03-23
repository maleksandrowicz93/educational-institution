package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.exception.InvalidPhoneNumberException;
import org.apache.commons.lang3.StringUtils;

public record PhoneNumber(String value) {
    public PhoneNumber {
        if (StringUtils.isBlank(value)) {
            throw new InvalidPhoneNumberException();
        }
    }
}
