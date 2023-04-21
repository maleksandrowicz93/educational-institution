package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.api.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseCreatedEvent(
        @NonNull FacultyId facultyId,
        @NonNull CourseSnapshot courseSnapshot
) implements DomainEvent {
}
