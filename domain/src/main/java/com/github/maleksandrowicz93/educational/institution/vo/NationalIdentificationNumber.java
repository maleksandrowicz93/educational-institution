package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.exception.InvalidIdentificationNumberException;
import org.apache.commons.lang3.StringUtils;

public record NationalIdentificationNumber(String value) {
    public NationalIdentificationNumber {
        if (StringUtils.isBlank(value)) {
            throw new InvalidIdentificationNumberException();
        }
    }
}
