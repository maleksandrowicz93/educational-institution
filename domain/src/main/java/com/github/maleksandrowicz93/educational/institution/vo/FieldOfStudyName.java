package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public record FieldOfStudyName(@NonNull String value) {
    public FieldOfStudyName {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("Field of study name cannot be blank");
        }
    }
}
