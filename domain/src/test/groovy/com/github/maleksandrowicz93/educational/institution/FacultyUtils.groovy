package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.vo.BasicPersonName
import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.Email
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyManagementThresholds
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyName
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.NationalIdentificationNumber
import com.github.maleksandrowicz93.educational.institution.vo.PersonName
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData
import com.github.maleksandrowicz93.educational.institution.vo.PersonalIdentification
import com.github.maleksandrowicz93.educational.institution.vo.PhoneNumber
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication
import com.github.maleksandrowicz93.educational.institution.vo.StudentEnrollmentThresholds
import com.github.maleksandrowicz93.educational.institution.vo.StudentId
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.TestResult
import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import com.github.maleksandrowicz93.educational.institution.vo.YearsOfExperience

import static java.util.stream.Collectors.toSet

class FacultyUtils {

    static final def FACULTY_NAME = "Mechanical"
    static final def MAIN_FIELD_OF_STUDY = "Mechanics and Construction of Machines"
    static final def SECONDARY_FIELDS_OF_STUDY = Set.of("Mechatronics", "Automation and Robotics")
    static final def BASIC_THRESHOLD = new Threshold(2)

    private FacultyUtils() {}

    static def facultySetup(EducationalInstitutionId educationalInstitutionId) {
        FacultySetup.builder()
                .name(FACULTY_NAME)
                .educationalInstitutionId(educationalInstitutionId)
                .mainFieldOfStudyName(mainFieldOfStudyName())
                .secondaryFieldsOfStudyNames(secondaryFieldsOfStudyNames())
                .facultyManagementThresholds(facultyManagementThresholds(BASIC_THRESHOLD))
                .build()
    }

    private static def facultyManagementThresholds(Threshold maximumLedCourses) {
        FacultyManagementThresholds.builder()
                .professorHiringThresholds(ProfessorHiringThresholds.builder()
                        .minimumYearsOfExperience(BASIC_THRESHOLD)
                        .minimumKnownFieldsOfStudy(BASIC_THRESHOLD)
                        .maximumVacancies(BASIC_THRESHOLD)
                        .build())
                .studentEnrollmentThresholds(StudentEnrollmentThresholds.builder()
                        .minimumMainFieldOfStudyExamScore(BASIC_THRESHOLD)
                        .minimumSecondaryFieldsOfStudyExamsScore(BASIC_THRESHOLD)
                        .maximumVacancies(BASIC_THRESHOLD)
                        .build())
                .courseCreationThresholds(CourseCreationThresholds.builder()
                        .minimumCourseFieldsOfStudy(BASIC_THRESHOLD)
                        .maximumCourseFieldsOfStudy(BASIC_THRESHOLD)
                        .maximumFacultyCourses(BASIC_THRESHOLD)
                        .maximumProfessorCourses(maximumLedCourses)
                        .build())
                .build()
    }

    private static def mainFieldOfStudyName() {
        return new FieldOfStudyName(MAIN_FIELD_OF_STUDY);
    }

    private static def secondaryFieldsOfStudyNames() {
        SECONDARY_FIELDS_OF_STUDY.stream()
                .map { new FieldOfStudyName(it) }
                .collect(toSet())
    }

    static def newFaculty() {
        newFaculty(BASIC_THRESHOLD)
    }

    static def newFaculty(Threshold maximumLedCourses) {
        def facultyId = new FacultyId(UUID.randomUUID())
        FacultySnapshot.builder()
                .id(facultyId)
                .name(FACULTY_NAME)
                .educationalInstitutionId(new EducationalInstitutionId(UUID.randomUUID()))
                .facultyManagementThresholds(facultyManagementThresholds(maximumLedCourses))
                .mainFieldOfStudy(fieldOfStudy(MAIN_FIELD_OF_STUDY, facultyId))
                .secondaryFieldsOfStudy(secondaryFieldsOfStudy(facultyId))
                .professors(Set.of())
                .students(Set.of())
                .courses(Set.of())
                .build()
    }

    static def fieldOfStudy(String fieldOfStudyName, FacultyId facultyId) {
        FieldOfStudySnapshot.builder()
                .id(new FieldOfStudyId(UUID.randomUUID()))
                .name(fieldOfStudyName)
                .facultyId(facultyId)
                .build()
    }

    private static def secondaryFieldsOfStudy(FacultyId facultyId) {
        SECONDARY_FIELDS_OF_STUDY.stream()
                .map { fieldOfStudy(it, facultyId) }
                .collect(toSet())
    }

    static def basicFieldsOfStudy(FacultyId facultyId) {
        secondaryFieldsOfStudy(facultyId).stream()
                .map { it.id() }
                .collect(toSet())
    }

    static def professor(FacultyId facultyId) {
        ProfessorSnapshot.builder()
                .id(new ProfessorId(UUID.randomUUID()))
                .personalData(fakePersonalData())
                .facultyId(facultyId)
                .fieldsOfStudy(secondaryFieldsOfStudy(facultyId))
                .ledCourses(Set.of())
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

    static def fakePersonalData() {
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

    static def student(FacultyId facultyId) {
        StudentSnapshot.builder()
                .id(new StudentId(UUID.randomUUID()))
                .personalData(fakePersonalData())
                .facultyId(facultyId)
                .enrollmentState(EnrollmentState.ENROLLED)
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

    static def basicCourseProposition(ProfessorSnapshot hired, FacultyId facultyId) {
        courseProposition(hired, secondaryFieldsOfStudy(facultyId))
    }

    static def courseProposition(ProfessorSnapshot hired, Set<FieldOfStudySnapshot> fieldsOfStudy) {
        CourseProposition.builder()
                .name("Machines Construction Fundamentals")
                .professorId(hired.id())
                .facultyId(hired.facultyId())
                .fieldsOfStudy(fieldsOfStudy)
                .maximumNumberOfStudents(BASIC_THRESHOLD)
                .build()
    }
}
