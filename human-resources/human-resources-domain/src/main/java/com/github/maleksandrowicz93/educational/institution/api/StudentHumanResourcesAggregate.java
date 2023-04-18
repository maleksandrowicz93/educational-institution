package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.common.Aggregate;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentHumanResourcesSnapshot;

public interface StudentHumanResourcesAggregate
        extends Aggregate<StudentHumanResourcesSnapshot, FacultyId>, StudentHumanResources {
}
