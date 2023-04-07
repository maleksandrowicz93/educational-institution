package com.github.maleksandrowicz93.educational.institution.api.infrastructure;

import com.github.maleksandrowicz93.educational.institution.api.domain.core.CourseEnrollmentsRegistry;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CourseEnrollmentsRegistryAggregate extends CourseAggregate implements CourseEnrollmentsRegistry {
}
