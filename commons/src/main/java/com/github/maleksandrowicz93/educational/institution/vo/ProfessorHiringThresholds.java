package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record ProfessorHiringThresholds(
        @NonNull Threshold minimumKnownFieldsOfStudy,
        @NonNull Threshold minimumYearsOfExperience,
        @NonNull Threshold maximumVacancies
) {
}
