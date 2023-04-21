package com.github.maleksandrowicz93.educational.institution.api.aggregates;

import com.github.maleksandrowicz93.educational.institution.api.Aggregate;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;

public interface EducationalInstitutionAggregate
        extends Aggregate<EducationalInstitutionSnapshot, EducationalInstitutionId> {
}
