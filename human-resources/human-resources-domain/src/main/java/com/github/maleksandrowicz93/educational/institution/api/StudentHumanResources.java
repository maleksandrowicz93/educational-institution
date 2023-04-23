package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.events.StudentEnrolledEvent;
import com.github.maleksandrowicz93.educational.institution.events.StudentResignedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;

public interface StudentHumanResources
        extends HumanResources<StudentApplication, StudentEnrolledEvent, StudentResignedEvent, StudentId> {
}
