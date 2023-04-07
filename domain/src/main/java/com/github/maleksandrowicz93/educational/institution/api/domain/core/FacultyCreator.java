package com.github.maleksandrowicz93.educational.institution.api.domain.core;

import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;

public interface FacultyCreator {

    FacultySnapshot createFaculty(FacultySetup facultySetup);
}
