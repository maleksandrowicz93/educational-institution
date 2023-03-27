package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public record PhoneNumber(@NonNull String value) {
    public PhoneNumber {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Phone number cannot be blank.");
        }
    }
}
