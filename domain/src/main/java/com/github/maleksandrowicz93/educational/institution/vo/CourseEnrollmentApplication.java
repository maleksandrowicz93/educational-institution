package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseEnrollmentApplication(
        @NonNull StudentId studentId,
        @NonNull CourseId courseId
) {
}
