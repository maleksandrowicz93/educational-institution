package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.DeanOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.api.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.DeanOfficeSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;

public interface DeanOfficeRepository extends DomainRepository<DeanOfficeAggregate, DeanOfficeSnapshot, FacultyId> {
}
