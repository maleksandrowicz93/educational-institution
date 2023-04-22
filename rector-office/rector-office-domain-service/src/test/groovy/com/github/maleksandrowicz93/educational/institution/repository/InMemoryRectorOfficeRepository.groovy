package com.github.maleksandrowicz93.educational.institution.repository

import com.github.maleksandrowicz93.educational.institution.api.InMemoryRepository
import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeAggregate
import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeRepository
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot

class InMemoryRectorOfficeRepository
        extends InMemoryRepository<RectorOfficeAggregate, RectorOfficeSnapshot, RectorOfficeId>
        implements RectorOfficeRepository {
}
