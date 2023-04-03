package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseCreationThresholds(
        @NonNull Threshold minimumCourseFieldsOfStudy,
        @NonNull Threshold maximumCourseFieldsOfStudy,
        @NonNull Threshold maximumFacultyCourses,
        @NonNull Threshold maximumProfessorCourses
) {
}
