package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.BaseAggregate;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;
import lombok.experimental.SuperBuilder;

@SuperBuilder
abstract class EducationalInstitutionAggregate extends BaseAggregate<EducationalInstitutionSnapshot, EducationalInstitutionId> {
}
