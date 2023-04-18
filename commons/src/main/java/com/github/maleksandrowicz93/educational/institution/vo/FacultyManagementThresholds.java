package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record FacultyManagementThresholds(
        @NonNull ProfessorHiringThresholds professorHiringThresholds,
        @NonNull StudentEnrollmentThresholds studentEnrollmentThresholds,
        @NonNull CourseCreationThresholds courseCreationThresholds
) {
}
