package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.CourseEnrollmentRegistryAggregate;
import com.github.maleksandrowicz93.educational.institution.api.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentRegistrySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;

public interface CourseEnrollmentRegistryRepository
        extends DomainRepository<CourseEnrollmentRegistryAggregate, CourseEnrollmentRegistrySnapshot, CourseId> {
}
