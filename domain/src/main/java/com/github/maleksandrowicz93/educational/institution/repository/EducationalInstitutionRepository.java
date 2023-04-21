package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.EducationalInstitutionAggregate;
import com.github.maleksandrowicz93.educational.institution.api.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;

public interface EducationalInstitutionRepository extends
        DomainRepository<EducationalInstitutionAggregate, EducationalInstitutionSnapshot, EducationalInstitutionId> {
}
