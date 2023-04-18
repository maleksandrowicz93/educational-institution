package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record StudentEnrolledEvent(
        @NonNull StudentId studentId,
        @NonNull FacultyId facultyId
) implements InclusionEvent {
}
