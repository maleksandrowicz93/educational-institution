package com.github.maleksandrowicz93.educational.institution.api.infrastructure;

import com.github.maleksandrowicz93.educational.institution.api.business.CourseCreator;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class CourseCreatorAggregate extends FacultyAggregate implements CourseCreator {
}
