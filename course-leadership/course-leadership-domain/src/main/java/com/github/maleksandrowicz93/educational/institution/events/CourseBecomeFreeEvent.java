package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.api.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseBecomeFreeEvent(@NonNull CourseId courseId) implements DomainEvent {
}
