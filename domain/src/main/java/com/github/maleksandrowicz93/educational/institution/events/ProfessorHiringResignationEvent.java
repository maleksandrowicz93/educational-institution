package com.github.maleksandrowicz93.educational.institution.events;

import com.github.maleksandrowicz93.educational.institution.api.DomainEvent;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;

public record ProfessorHiringResignationEvent(ProfessorSnapshot professorSnapshot) implements DomainEvent {
}
