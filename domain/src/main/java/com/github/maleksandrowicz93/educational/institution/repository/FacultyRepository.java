package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.FacultyAggregate;
import com.github.maleksandrowicz93.educational.institution.api.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;

public interface FacultyRepository extends DomainRepository<FacultyAggregate, FacultySnapshot, FacultyId> {
}
