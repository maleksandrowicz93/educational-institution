package com.github.maleksandrowicz93.educational.institution.api.aggregates;

import com.github.maleksandrowicz93.educational.institution.common.BaseAggregate;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class FacultyAggregate extends BaseAggregate<FacultySnapshot, FacultyId> {
}
