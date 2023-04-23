package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHumanResourcesSnapshot;

public interface ProfessorHumanResourcesAggregate
        extends Aggregate<ProfessorHumanResourcesSnapshot, FacultyId>, ProfessorHumanResources {
}
