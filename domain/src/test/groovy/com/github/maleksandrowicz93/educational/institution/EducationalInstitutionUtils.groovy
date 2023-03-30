package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.vo.BasicPersonName
import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds
import com.github.maleksandrowicz93.educational.institution.vo.CourseManagementThresholds
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSetup
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionThresholds
import com.github.maleksandrowicz93.educational.institution.vo.Email
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyName
import com.github.maleksandrowicz93.educational.institution.vo.NationalIdentificationNumber
import com.github.maleksandrowicz93.educational.institution.vo.PersonName
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData
import com.github.maleksandrowicz93.educational.institution.vo.PersonalIdentification
import com.github.maleksandrowicz93.educational.institution.vo.PhoneNumber
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorAvailabilityThresholds
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication
import com.github.maleksandrowicz93.educational.institution.vo.StudentEnrollmentThresholds
import com.github.maleksandrowicz93.educational.institution.vo.TestResult
import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import com.github.maleksandrowicz93.educational.institution.vo.YearsOfExperience

import static java.util.stream.Collectors.toSet

class EducationalInstitutionUtils {

    private static def BASIC_THRESHOLD = new Threshold(2)

    private EducationalInstitutionUtils() {}

    static def educationalInstitutionSnapshot() {
        educationalInstitutionSnapshot(BASIC_THRESHOLD)
    }

    static def educationalInstitutionSnapshot(Threshold maximumLedCourses) {
        EducationalInstitutionSnapshot.builder()
                .id(new EducationalInstitutionId(UUID.randomUUID()))
                .setup(EducationalInstitutionSetup.builder()
                        .name("Wroclaw University of Technology")
                        .thresholds(EducationalInstitutionThresholds.builder()
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
                                .professorAvailabilityThresholds(ProfessorAvailabilityThresholds.builder()
                                        .maximumLedCourses(maximumLedCourses)
                                        .build())
                                .courseCreationThresholds(CourseCreationThresholds.builder()
                                        .minimumCourseFieldsOfStudy(BASIC_THRESHOLD)
                                        .maximumCourseFieldsOfStudy(BASIC_THRESHOLD)
                                        .maximumFacultyCourses(BASIC_THRESHOLD)
                                        .build())
                                .courseManagementThresholds(CourseManagementThresholds.builder()
                                        .minimumQuantityOfMaximumEnrollments(BASIC_THRESHOLD)
                                        .minimumEnrollmentsCourseCannotBeClosed(BASIC_THRESHOLD)
                                        .build())
                                .build())
                        .build())
                .faculties(Set.of())
                .build()
    }

    static def facultySetup() {
        FacultySetup.builder()
                .name("Mechanical")
                .mainFieldOfStudyName(new FieldOfStudyName("Mechanics and Construction of Machines"))
                .secondaryFieldsOfStudyNames(Set.of(
                        new FieldOfStudyName("Mechatronics"),
                        new FieldOfStudyName("Automation and Robotics")
                ))
                .build()
    }

    static def basicFieldsOfStudy(FacultySnapshot faculty) {
        faculty.secondaryFieldsOfStudy().stream()
                .map { it.id() }
                .collect(toSet())
    }

    static def basicProfessorApplication(FacultySnapshot faculty) {
        def yearsOfExperience = 3
        def fieldsOfStudy = basicFieldsOfStudy(faculty)
        professorApplication(faculty.id(), yearsOfExperience, fieldsOfStudy)
    }

    static def professorApplication(FacultyId facultyId, int yearsOfExperience, Set<FieldOfStudyId> fieldsOfStudy) {
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

    static def basicCourseProposition(ProfessorSnapshot hired, FacultySnapshot faculty) {
        courseProposition(hired, basicFieldsOfStudy(faculty))
    }

    static def courseProposition(ProfessorSnapshot hired, Set<FieldOfStudyId> fieldsOfStudy) {
        CourseProposition.builder()
                .name("Machines Construction Fundamentals")
                .professorId(hired.id())
                .facultyId(hired.facultyId())
                .fieldsOfStudy(fieldsOfStudy)
                .maximumNumberOfStudents(BASIC_THRESHOLD)
                .build()
    }
}
