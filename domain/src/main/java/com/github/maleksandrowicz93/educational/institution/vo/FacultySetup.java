package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Builder
public record FacultySetup(
        @NonNull String facultyName,
        @NonNull Set<FieldOfStudySnapshot> fieldsOfStudy
) {
    public FacultySetup {
        if (StringUtils.isBlank(facultyName)) {
            throw new IllegalArgumentException("Faculty name cannot be blank.");
        }
    }
}
