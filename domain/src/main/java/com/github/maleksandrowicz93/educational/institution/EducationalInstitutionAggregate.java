package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Aggregate;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;

interface EducationalInstitutionAggregate extends Aggregate<EducationalInstitutionSnapshot> {

    FacultySnapshot createFaculty(FacultySetup facultySetup);
}
