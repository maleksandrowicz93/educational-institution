package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record EducationalInstitutionThresholds(
        @NonNull FacultyManagementThresholds facultyManagementThresholds,
        @NonNull CourseManagementThresholds courseManagementThresholds
) {
}
