package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record EducationalInstitutionThresholds(
        @NonNull ProfessorHiringThresholds professorHiringThresholds,
        @NonNull StudentEnrollmentThresholds studentEnrollmentThresholds,
        @NonNull CourseCreationThresholds courseCreationThresholds,
        @NonNull ProfessorAvailabilityThresholds professorAvailabilityThresholds,
        @NonNull CourseManagementThresholds courseManagementThresholds
) {
}
