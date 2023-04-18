package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Builder
public record BasicPersonName(
        @NonNull String firstName,
        @NonNull String lastName
) {
    public BasicPersonName {
        if (StringUtils.isAnyBlank(firstName, lastName)) {
            throw new IllegalArgumentException("Firstname and Lastname cannot be blank.");
        }
    }
}
