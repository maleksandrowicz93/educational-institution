package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.api.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import lombok.Builder;

@Builder
public record CourseClosedEvent(CourseId courseId) implements DomainEvent {
}
