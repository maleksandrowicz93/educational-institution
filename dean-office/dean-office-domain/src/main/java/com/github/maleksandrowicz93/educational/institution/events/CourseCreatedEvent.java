package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.common.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;

public record CourseCreatedEvent(
        FacultyId facultyId,
        CourseId courseId
) implements DomainEvent {
}
