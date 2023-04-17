package com.github.maleksandrowicz93.educational.institution.api.domain.service;

import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public interface FacultyManagementService {

    Result<ProfessorSnapshot> resignFromEmployment(ProfessorSnapshot professorSnapshot);

    Result<StudentSnapshot> resignFromEnrollment(StudentSnapshot studentSnapshot);
}
