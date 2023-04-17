package com.github.maleksandrowicz93.educational.institution.api.domain.service;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;

public interface FacultyCreationService {

    Result<FacultySnapshot> createFaculty(FacultySetup facultySetup);
}
