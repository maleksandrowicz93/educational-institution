package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseOvertakenEvent(
        @NonNull CourseId courseId,
        @NonNull ProfessorId professorId
) {
}
