package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record StudentResignedEvent(
        @NonNull StudentId studentId,
        @NonNull FacultyId facultyId
) implements ResignationEvent {
}
