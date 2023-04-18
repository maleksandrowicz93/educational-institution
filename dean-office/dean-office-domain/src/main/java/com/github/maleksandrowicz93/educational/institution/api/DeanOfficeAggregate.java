package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.common.Aggregate;
import com.github.maleksandrowicz93.educational.institution.vo.DeanOfficeSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;

public interface DeanOfficeAggregate extends Aggregate<DeanOfficeSnapshot, FacultyId>, DeanOffice {
}
