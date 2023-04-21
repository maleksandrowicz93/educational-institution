package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentRegistrySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;

public interface CourseEnrollmentRegistryAggregate
        extends Aggregate<CourseEnrollmentRegistrySnapshot, CourseId>, CourseEnrollmentRegistry {
}
