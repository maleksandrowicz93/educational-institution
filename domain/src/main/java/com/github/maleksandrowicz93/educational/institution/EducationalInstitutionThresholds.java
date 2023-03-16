package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record EducationalInstitutionThresholds(
        Threshold minimumKnownFieldsOfStudy,
        Threshold minimumYearsOfExperience,
        Threshold minimumMainFieldsOfStudyExamScore,
        Threshold minimumSecondaryFieldsOfStudyExamScore,
        Threshold minimumCourseFieldsOfStudyAmount,
        Threshold maximumCourseFieldsOfStudyAmount,
        Threshold maximumLedCourses,
        Threshold maximumFacultyCourses,
        Threshold maximumCoursesAmountMatchingGivenFieldsOfStudy,
        Threshold minimumFieldsOfStudyAmountMakingCoursesMatched
) {
}
