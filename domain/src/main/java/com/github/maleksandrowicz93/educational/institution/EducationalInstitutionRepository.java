package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;

interface EducationalInstitutionRepository extends
        DomainRepository<EducationalInstitutionSnapshot, EducationalInstitutionId> {
}
