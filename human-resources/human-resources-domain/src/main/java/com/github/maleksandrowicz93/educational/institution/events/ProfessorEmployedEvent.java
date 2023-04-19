package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record ProfessorEmployedEvent(
        @NonNull FacultyId facultyId,
        @NonNull ProfessorSnapshot professorSnapshot
) implements InclusionEvent {
}
