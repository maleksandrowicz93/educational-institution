package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

@Builder
public record CourseOvertakingApplication(
        @NonNull ProfessorId professorId,
        @Singular(value = "fieldOfStudy", ignoreNullCollections = true) Set<FieldOfStudyId> fieldsOfStudy,
        @Singular(ignoreNullCollections = true) Set<CourseId> ledCourses
) {
}
