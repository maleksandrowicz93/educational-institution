package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

@Builder
public record FacultySetup(
        @NonNull String facultyName,
        @NonNull Set<FieldOfStudySnapshot> fieldsOfStudy
) {
}
