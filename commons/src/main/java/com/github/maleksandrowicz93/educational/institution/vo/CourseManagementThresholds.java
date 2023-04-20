package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseManagementThresholds(
        @NonNull CourseOvertakingThresholds courseOvertakingThresholds,
        @NonNull ForCourseEnrollmentThresholds forCourseEnrollmentThresholds
) {
}
