package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.exception.InvalidPersonNameException;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Builder
public record PersonName(
        String firstName,
        String secondName,
        String lastName
) {
    public PersonName {
        if (StringUtils.isBlank(firstName) || StringUtils.isBlank(lastName)) {
            throw new InvalidPersonNameException();
        }
    }

    PersonName(String firstName, String lastName) {
        this(firstName, null, lastName);
    }
}
