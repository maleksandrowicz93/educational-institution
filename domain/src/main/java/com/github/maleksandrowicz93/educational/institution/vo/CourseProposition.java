package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

@Builder
public record CourseProposition(
        @NonNull String name,
        @NonNull ProfessorId professorId,
        @NonNull Set<FieldOfStudySnapshot> fieldsOfStudy,
        @NonNull Threshold maximumNumberOfStudents
) {
}
