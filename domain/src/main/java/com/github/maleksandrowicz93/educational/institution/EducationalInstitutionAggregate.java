package com.github.maleksandrowicz93.educational.institution;

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
