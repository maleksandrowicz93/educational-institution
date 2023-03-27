package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public record NationalIdentificationNumber(@NonNull String value) {
    public NationalIdentificationNumber {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("National identification number cannot be blank.");
        }
    }
}
