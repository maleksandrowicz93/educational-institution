package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.StudentHumanResourcesAggregate;
import com.github.maleksandrowicz93.educational.institution.common.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentHumanResourcesSnapshot;

public interface StudentHumanResourcesRepository
        extends DomainRepository<StudentHumanResourcesAggregate, StudentHumanResourcesSnapshot, FacultyId> {
}
