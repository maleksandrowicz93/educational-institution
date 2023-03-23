package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.common.Aggregate;
import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingApplication;
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup;
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot;

import java.util.Optional;

interface EducationalInstitutionAggregate extends Aggregate<EducationalInstitutionSnapshot> {

    FacultySnapshot createFaculty(FacultySetup facultySetup);
    Optional<ProfessorSnapshot> considerHiring(ProfessorApplication professorApplication);
    ProfessorSnapshot receiveHiringResignation(ProfessorId professorId);
    Optional<StudentSnapshot> considerEnrollment(StudentApplication studentApplication);
    StudentSnapshot receiveEnrollmentResignation(StudentId studentId);
    Optional<CourseSnapshot> considerCourseCreation(CourseProposition courseProposition);
    CourseSnapshot receiveCourseLeadingResignation(CourseId courseId);
    Optional<CourseSnapshot> considerCourseOvertaking(CourseOvertakingApplication courseOvertakingApplication);
    Optional<CourseSnapshot> considerCourseEnrollment(CourseEnrollmentApplication courseEnrollmentApplication);
    Optional<CourseSnapshot> considerClosingCourse(CourseId courseId);
}
