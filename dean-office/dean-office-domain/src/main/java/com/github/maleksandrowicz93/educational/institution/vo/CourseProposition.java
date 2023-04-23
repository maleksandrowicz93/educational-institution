package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Builder
public record CourseProposition(
        @NonNull String name,
        @NonNull ProfessorId leadingProfessor,
        @Singular(value = "fieldOfStudy", ignoreNullCollections = true) Set<FieldOfStudyId> fieldsOfStudy,
        @NonNull Threshold maximumNumberOfStudents
) {
    public CourseProposition {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("CourseSnapshot name cannot be blank.");
        }
    }
}
