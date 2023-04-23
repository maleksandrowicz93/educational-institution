package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Builder
public record FacultySetup(
        @NonNull String name,
        @NonNull FieldOfStudyName mainFieldOfStudyName,
        @Singular(ignoreNullCollections = true) Set<FieldOfStudyName> secondaryFieldsOfStudyNames
) {
    public FacultySetup {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Faculty name cannot be blank.");
        }
    }
}