package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

@Builder
public record Faculty(
        @NonNull FacultyId id,
        @NonNull String name
) {
    public Faculty {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Faculty name cannot be blank");
        }
    }
}
