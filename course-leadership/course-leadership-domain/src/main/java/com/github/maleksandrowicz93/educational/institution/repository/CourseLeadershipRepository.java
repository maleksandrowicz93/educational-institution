package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.CourseLeadershipAggregate;
import com.github.maleksandrowicz93.educational.institution.api.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseLeadershipSnapshot;

interface CourseLeadershipRepository
        extends DomainRepository<CourseLeadershipAggregate, CourseLeadershipSnapshot, CourseId> {
}
