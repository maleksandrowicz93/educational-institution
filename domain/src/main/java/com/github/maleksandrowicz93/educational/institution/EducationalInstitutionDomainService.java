package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResult;
import com.github.maleksandrowicz93.educational.institution.results.CourseLeadingResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.FacultyCreationResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

interface EducationalInstitutionDomainService {

    FacultyCreationResult createFaculty(FacultySetup facultySetup);

    CourseCreationResult createCourse(CourseProposition courseProposition);

    EmploymentResignationResult resignFromEmployment(ProfessorSnapshot professorSnapshot);

    EnrollmentResignationResult resignFromEnrollment(StudentSnapshot studentSnapshot);

    CourseLeadingResignationResult resignFromLeadingCourse(CourseId courseId);

    CourseClosingResult closeCourse(CourseId courseId);
}
