package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseOvertakingApplication(
        @NonNull ProfessorId professorId,
        @NonNull CourseId courseId
) {
}
