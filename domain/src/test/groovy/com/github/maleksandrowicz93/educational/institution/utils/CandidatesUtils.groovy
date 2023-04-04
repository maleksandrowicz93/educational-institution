package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.vo.BasicPersonName
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.Email
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.NationalIdentificationNumber
import com.github.maleksandrowicz93.educational.institution.vo.PersonName
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData
import com.github.maleksandrowicz93.educational.institution.vo.PersonalIdentification
import com.github.maleksandrowicz93.educational.institution.vo.PhoneNumber
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication
import com.github.maleksandrowicz93.educational.institution.vo.StudentId
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.TestResult
import com.github.maleksandrowicz93.educational.institution.vo.YearsOfExperience

import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.secondaryFieldsOfStudy
import static java.util.stream.Collectors.toSet

class CandidatesUtils {

    private CandidatesUtils() {}

    static def newProfessor(FacultyId facultyId) {
        professor(facultyId, Set.of())

    }

    static def professorLeadingCourse(FacultyId facultyId, CourseId courseId) {
        professor(facultyId, Set.of(courseId))
    }

    static def professorLeadingCourses(FacultyId facultyId, Set<CourseId> courses) {
        professor(facultyId, courses)
    }

    private static def professor(FacultyId facultyId, Set<CourseId> ledCourses) {
        ProfessorSnapshot.builder()
                .id(new ProfessorId(UUID.randomUUID()))
                .personalData(fakePersonalData())
                .facultyId(facultyId)
                .fieldsOfStudy(secondaryFieldsOfStudy(facultyId))
                .ledCourses(ledCourses)
                .employmentState(EmploymentState.EMPLOYED)
                .build()
    }

    static def basicProfessorApplication(FacultyId facultyId) {
        def yearsOfExperience = 3
        def fieldsOfStudy = secondaryFieldsOfStudy(facultyId)
        professorApplication(facultyId, yearsOfExperience, fieldsOfStudy)
    }

    static def professorApplication(
            FacultyId facultyId,
            int yearsOfExperience,
            Set<FieldOfStudySnapshot> fieldsOfStudy
    ) {
        ProfessorApplication.builder()
                .facultyId(facultyId)
                .yearsOfExperience(new YearsOfExperience(yearsOfExperience))
                .fieldsOfStudy(fieldsOfStudy)
                .personalData(fakePersonalData())
                .build()
    }

    private static def fakePersonalData() {
        PersonalData.builder()
                .personalIdentification(PersonalIdentification.builder()
                        .country(Locale.ITALY)
                        .nationalIdentificationNumber(new NationalIdentificationNumber("123456789"))
                        .build())
                .personName(PersonName.builder()
                        .basicPersonName(BasicPersonName.builder()
                                .firstName("Mateusz")
                                .lastName("Aleksandrowicz")
                                .build())
                        .secondName("Grzegorz")
                        .build())
                .email(new Email("email@gmail.com"))
                .phoneNumber(new PhoneNumber("123456789"))
                .build()
    }

    static def newStudent(FacultyId facultyId) {
        student(facultyId, Set.of())
    }

    static def studentEnrolledForCourse(FacultyId facultyId, CourseId courseId) {
        student(facultyId, Set.of(courseId))
    }

    static def studentEnrolledForCourses(FacultyId facultyId, Set<CourseId> courses) {
        student(facultyId, courses)
    }

    private static def student(FacultyId facultyId, Set<CourseId> courses) {
        StudentSnapshot.builder()
                .id(new StudentId(UUID.randomUUID()))
                .personalData(fakePersonalData())
                .facultyId(facultyId)
                .enrollmentState(EnrollmentState.ENROLLED)
                .courses(courses)
                .build()
    }

    static def basicStudentApplication(FacultySnapshot faculty) {
        studentApplication(faculty, 80, 90)
    }

    static def studentApplication(FacultySnapshot faculty, int mainTestResult, int secondaryTestResult) {
        StudentApplication.builder()
                .facultyId(faculty.id())
                .personalData(fakePersonalData())
                .mainTestResult(new TestResult(mainTestResult, faculty.mainFieldOfStudy().id()))
                .secondaryTestsResults(faculty.secondaryFieldsOfStudy().stream()
                        .map { field -> new TestResult(secondaryTestResult, field.id()) }
                        .collect(toSet()))
                .build()
    }
}
