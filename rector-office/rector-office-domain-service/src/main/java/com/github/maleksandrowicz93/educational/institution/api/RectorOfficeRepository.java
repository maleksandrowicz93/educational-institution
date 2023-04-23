package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot;

public interface RectorOfficeRepository
        extends DomainRepository<RectorOfficeAggregate, RectorOfficeSnapshot, RectorOfficeId> {
}
