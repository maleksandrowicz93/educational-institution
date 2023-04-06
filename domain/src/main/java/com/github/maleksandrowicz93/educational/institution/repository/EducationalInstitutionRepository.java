package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.common.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;

public interface EducationalInstitutionRepository extends
        DomainRepository<EducationalInstitutionSnapshot, EducationalInstitutionId> {
}
