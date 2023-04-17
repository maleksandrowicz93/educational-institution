package com.github.maleksandrowicz93.educational.institution.reposiotry

import com.github.maleksandrowicz93.educational.institution.api.aggregates.EducationalInstitutionAggregate
import com.github.maleksandrowicz93.educational.institution.common.InMemoryRepository
import com.github.maleksandrowicz93.educational.institution.repository.EducationalInstitutionRepository
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot

class InMemoryEducationalInstitutionRepository
        extends InMemoryRepository<EducationalInstitutionAggregate, EducationalInstitutionSnapshot,
                EducationalInstitutionId>
        implements EducationalInstitutionRepository {
}
