package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record StudentEnrolledEvent(
        @NonNull FacultyId facultyId,
        @NonNull StudentSnapshot studentSnapshot
) implements InclusionEvent {
}
