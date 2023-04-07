package com.github.maleksandrowicz93.educational.institution.api.domain.service;

import com.github.maleksandrowicz93.educational.institution.results.FacultyCreationResult;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;

public interface FacultyCreationService {

    FacultyCreationResult createFaculty(FacultySetup facultySetup);
}
