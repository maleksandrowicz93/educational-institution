package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ForCourseEnrollmentThresholds(
        @NonNull Threshold maximumNumberOfEnrolledStudents,
        @NonNull Threshold minimumEnrollmentsCourseCannotBeClosed
) {
}
