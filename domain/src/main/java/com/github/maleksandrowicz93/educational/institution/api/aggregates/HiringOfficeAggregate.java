package com.github.maleksandrowicz93.educational.institution.api.aggregates;

import com.github.maleksandrowicz93.educational.institution.api.domain.core.HiringOffice;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class HiringOfficeAggregate extends FacultyAggregate implements HiringOffice {
}
