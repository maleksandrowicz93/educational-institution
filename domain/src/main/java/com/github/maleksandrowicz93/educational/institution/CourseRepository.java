package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;

interface CourseRepository extends DomainRepository<CourseSnapshot, CourseId> {
}