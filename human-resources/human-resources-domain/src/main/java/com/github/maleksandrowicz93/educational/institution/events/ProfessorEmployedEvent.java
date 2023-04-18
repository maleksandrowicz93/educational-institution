package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record ProfessorEmployedEvent(
        @NonNull ProfessorId professorId,
        @NonNull FacultyId facultyId
) implements InclusionEvent {
}
