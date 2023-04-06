package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Aggregate;
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResult;
import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResult;
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResult;
import com.github.maleksandrowicz93.educational.institution.results.HiringResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;

interface FacultyAggregate extends Aggregate<FacultySnapshot, FacultyId> {

    HiringResult considerHiring(ProfessorApplication professorApplication);

    EmploymentResignationResult receiveHiringResignation(ProfessorId professorId);

    EnrollmentResult considerEnrollment(StudentApplication studentApplication);

    EnrollmentResignationResult receiveEnrollmentResignation(StudentId studentId);

    CourseCreationResult considerCourseCreation(CourseProposition courseProposition);
}
