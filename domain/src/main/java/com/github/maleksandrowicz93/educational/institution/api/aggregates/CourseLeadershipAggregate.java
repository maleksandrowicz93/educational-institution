package com.github.maleksandrowicz93.educational.institution.api.aggregates;

import com.github.maleksandrowicz93.educational.institution.api.domain.core.CourseLeadership;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CourseLeadershipAggregate extends CourseAggregate implements CourseLeadership {
}
