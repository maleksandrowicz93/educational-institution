package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Builder
public record CourseProposition(
        @NonNull String name,
        @NonNull ProfessorId professorId,
        @NonNull Set<FieldOfStudySnapshot> fieldsOfStudy,
        @NonNull Threshold maximumNumberOfStudents
) {
    public CourseProposition {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Course name cannot be blank.");
        }
    }
}
