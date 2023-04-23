package com.github.maleksandrowicz93.educational.institution.api;

import com.github.maleksandrowicz93.educational.institution.events.FacultyCreatedEvent;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId;

public interface RectorOfficeService {

    Result<FacultyCreatedEvent> createFaculty(RectorOfficeId rectorOfficeId, FacultySetup facultySetup);
}
