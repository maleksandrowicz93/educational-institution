package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.events.FacultyCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;

public interface RectorOffice {

    Result<FacultyCreatedEvent> createFaculty(FacultySetup facultySetup);
}
