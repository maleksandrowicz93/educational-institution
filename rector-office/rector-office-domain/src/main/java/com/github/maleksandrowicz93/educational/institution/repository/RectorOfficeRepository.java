package com.github.maleksandrowicz93.educational.institution.repository;

import com.github.maleksandrowicz93.educational.institution.api.RectorOfficeAggregate;
import com.github.maleksandrowicz93.educational.institution.api.DomainRepository;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot;

public interface RectorOfficeRepository
        extends DomainRepository<RectorOfficeAggregate, RectorOfficeSnapshot, RectorOfficeId> {
}
