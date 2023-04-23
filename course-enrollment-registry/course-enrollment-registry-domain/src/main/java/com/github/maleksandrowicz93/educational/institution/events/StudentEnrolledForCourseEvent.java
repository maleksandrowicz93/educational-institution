package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.api.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import lombok.Builder;

@Builder
public record StudentEnrolledForCourseEvent(
        CourseId courseId,
        StudentId studentId
) implements DomainEvent {
}
