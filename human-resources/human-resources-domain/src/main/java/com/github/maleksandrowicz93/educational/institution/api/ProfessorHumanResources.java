package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.events.ProfessorEmployedEvent;
import com.github.maleksandrowicz93.educational.institution.events.ProfessorResignedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;

public interface ProfessorHumanResources
        extends HumanResources<ProfessorApplication, ProfessorEmployedEvent, ProfessorResignedEvent, ProfessorId> {
}
