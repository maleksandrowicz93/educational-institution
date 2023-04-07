package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.BaseAggregate;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import lombok.experimental.SuperBuilder;

@SuperBuilder
abstract class FacultyAggregate extends BaseAggregate<FacultySnapshot, FacultyId> {
}
