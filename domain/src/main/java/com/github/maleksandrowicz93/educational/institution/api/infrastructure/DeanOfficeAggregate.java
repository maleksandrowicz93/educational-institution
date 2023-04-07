package com.github.maleksandrowicz93.educational.institution.api.infrastructure;

import com.github.maleksandrowicz93.educational.institution.api.domain.core.DeanOffice;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class DeanOfficeAggregate extends FacultyAggregate implements DeanOffice {
}
