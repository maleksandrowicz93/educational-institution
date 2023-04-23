package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot;

public interface RectorOfficeAggregate extends Aggregate<RectorOfficeSnapshot, RectorOfficeId>, RectorOffice {
}
