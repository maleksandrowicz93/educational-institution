package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseManagementThresholds(
        @NonNull Threshold minimumQuantityOfMaximumEnrollments,
        @NonNull Threshold minimumEnrollmentsCourseCannotBeClosed
) {
}
