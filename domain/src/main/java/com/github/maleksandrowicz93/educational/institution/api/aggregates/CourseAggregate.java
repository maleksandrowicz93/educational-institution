package com.github.maleksandrowicz93.educational.institution.api.aggregates;

import com.github.maleksandrowicz93.educational.institution.common.BaseAggregate;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CourseAggregate extends BaseAggregate<CourseSnapshot, CourseId> {
}
