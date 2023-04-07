package com.github.maleksandrowicz93.educational.institution.api.infrastructure;

import com.github.maleksandrowicz93.educational.institution.api.business.CourseLeadership;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CourseLeadershipAggregate extends CourseAggregate implements CourseLeadership {
}
