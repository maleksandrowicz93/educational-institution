package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;

interface FacultyCreator extends EducationalInstitutionAggregate {

    FacultySnapshot createFaculty(FacultySetup facultySetup);
}