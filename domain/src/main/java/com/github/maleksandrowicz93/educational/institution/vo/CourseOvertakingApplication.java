package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseOvertakingApplication(
        @NonNull ProfessorSnapshot applyingProfessor,
        @NonNull CourseSnapshot courseToBeOvertaken
) {
}
