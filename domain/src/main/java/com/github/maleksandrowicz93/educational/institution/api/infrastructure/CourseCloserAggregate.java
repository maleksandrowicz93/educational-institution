package com.github.maleksandrowicz93.educational.institution.api.infrastructure;

import com.github.maleksandrowicz93.educational.institution.api.business.CourseCloser;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CourseCloserAggregate extends CourseAggregate implements CourseCloser {
}
