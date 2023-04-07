package com.github.maleksandrowicz93.educational.institution.api.business;

import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResult;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

public interface FacultyManagementService {

    EmploymentResignationResult resignFromEmployment(ProfessorSnapshot professorSnapshot);

    EnrollmentResignationResult resignFromEnrollment(StudentSnapshot studentSnapshot);
}
