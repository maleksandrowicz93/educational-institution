package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.vo.BasicPersonName
import com.github.maleksandrowicz93.educational.institution.vo.Email
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.NationalIdentificationNumber
import com.github.maleksandrowicz93.educational.institution.vo.PersonName
import com.github.maleksandrowicz93.educational.institution.vo.PersonalData
import com.github.maleksandrowicz93.educational.institution.vo.PersonalIdentification
import com.github.maleksandrowicz93.educational.institution.vo.PhoneNumber
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorApplication
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHumanResourcesSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.StudentApplication
import com.github.maleksandrowicz93.educational.institution.vo.StudentHumanResourcesSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.StudentId
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.TestResult
import com.github.maleksandrowicz93.educational.institution.vo.YearsOfExperience

import static java.util.stream.Collectors.toSet

class CandidatesUtils {

    private CandidatesUtils() {}

    static def newProfessor() {
        ProfessorSnapshot.builder()
                .id(new ProfessorId(UUID.randomUUID()))
                .personalData(fakePersonalData())
                .employmentState(EmploymentState.EMPLOYED)
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

    static def basicProfessorApplication(ProfessorHumanResourcesSnapshot humanResources) {
        def yearsOfExperience = 3
        def fieldsOfStudy = humanResources.secondaryFieldsOfStudy()
        professorApplication(yearsOfExperience, fieldsOfStudy)
    }

    static def professorApplication(
            int yearsOfExperience,
            Set<FieldOfStudyId> fieldsOfStudy
    ) {
        ProfessorApplication.builder()
                .yearsOfExperience(new YearsOfExperience(yearsOfExperience))
                .fieldsOfStudy(fieldsOfStudy)
                .personalData(fakePersonalData())
                .build()
    }

    static def newStudent() {
        StudentSnapshot.builder()
                .id(new StudentId(UUID.randomUUID()))
                .personalData(fakePersonalData())
                .enrollmentState(EnrollmentState.ENROLLED)
                .build()
    }

    static def basicStudentApplication(StudentHumanResourcesSnapshot humanResources) {
        studentApplication(humanResources, 80, 90)
    }

    static def studentApplication(
            StudentHumanResourcesSnapshot humanResources,
            int mainTestResult,
            int secondaryTestResult
    ) {
        StudentApplication.builder()
                .personalData(fakePersonalData())
                .mainTestResult(new TestResult(mainTestResult, humanResources.mainFieldOfStudy()))
                .secondaryTestsResults(humanResources.secondaryFieldsOfStudy().stream()
                        .map { fieldOfStudy -> new TestResult(secondaryTestResult, fieldOfStudy) }
                        .collect(toSet()))
                .build()
    }
}
