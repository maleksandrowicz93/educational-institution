package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record EducationalInstitutionThresholds(
        @NonNull Threshold minimumKnownFieldsOfStudy,
        @NonNull Threshold minimumYearsOfExperience,
        @NonNull Threshold minimumMainFieldsOfStudyExamScore,
        @NonNull Threshold minimumSecondaryFieldsOfStudyExamScore,
        @NonNull Threshold minimumCourseFieldsOfStudyAmount,
        @NonNull Threshold maximumCourseFieldsOfStudyAmount,
        @NonNull Threshold maximumLedCourses,
        @NonNull Threshold maximumFacultyCourses,
        @NonNull Threshold maximumCoursesAmountMatchingGivenFieldsOfStudy,
        @NonNull Threshold minimumFieldsOfStudyAmountMakingCoursesMatched
) {
}
