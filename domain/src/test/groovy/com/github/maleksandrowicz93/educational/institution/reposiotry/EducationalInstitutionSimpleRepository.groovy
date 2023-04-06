package com.github.maleksandrowicz93.educational.institution.reposiotry

import com.github.maleksandrowicz93.educational.institution.common.SimpleRepository
import com.github.maleksandrowicz93.educational.institution.repository.EducationalInstitutionRepository
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot

class EducationalInstitutionSimpleRepository
        extends SimpleRepository<EducationalInstitutionSnapshot, EducationalInstitutionId>
        implements EducationalInstitutionRepository {
}
