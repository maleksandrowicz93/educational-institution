package com.github.maleksandrowicz93.educational.institution.api.infrastructure;

import com.github.maleksandrowicz93.educational.institution.api.business.HiringOffice;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class HiringOfficeAggregate extends FacultyAggregate implements HiringOffice {
}
