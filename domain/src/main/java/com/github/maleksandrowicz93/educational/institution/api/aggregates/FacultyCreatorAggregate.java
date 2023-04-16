package com.github.maleksandrowicz93.educational.institution.api.aggregates;

import com.github.maleksandrowicz93.educational.institution.api.domain.core.FacultyCreator;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class FacultyCreatorAggregate extends EducationalInstitutionAggregate implements FacultyCreator {
}
