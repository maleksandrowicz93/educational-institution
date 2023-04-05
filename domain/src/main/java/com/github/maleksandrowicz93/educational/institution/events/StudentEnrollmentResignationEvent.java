package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.common.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public record StudentEnrollmentResignationEvent(StudentSnapshot studentSnapshot) implements DomainEvent {
}
