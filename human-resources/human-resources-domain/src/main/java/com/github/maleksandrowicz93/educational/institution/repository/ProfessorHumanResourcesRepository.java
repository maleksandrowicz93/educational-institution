package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.ProfessorHumanResourcesAggregate;
import com.github.maleksandrowicz93.educational.institution.api.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHumanResourcesSnapshot;

public interface ProfessorHumanResourcesRepository
        extends DomainRepository<ProfessorHumanResourcesAggregate, ProfessorHumanResourcesSnapshot, FacultyId> {
}
