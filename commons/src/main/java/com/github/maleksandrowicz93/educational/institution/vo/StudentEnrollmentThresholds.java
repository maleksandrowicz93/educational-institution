package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record StudentEnrollmentThresholds(
        @NonNull Threshold minimumMainFieldOfStudyExamScore,
        @NonNull Threshold minimumSecondaryFieldsOfStudyExamsScore,
        @NonNull Threshold maximumVacancies
) {
}
