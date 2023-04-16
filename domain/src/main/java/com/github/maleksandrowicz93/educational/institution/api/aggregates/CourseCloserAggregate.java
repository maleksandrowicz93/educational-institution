package com.github.maleksandrowicz93.educational.institution.api.aggregates;

import com.github.maleksandrowicz93.educational.institution.api.domain.core.CourseCloser;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CourseCloserAggregate extends CourseAggregate implements CourseCloser {
}
