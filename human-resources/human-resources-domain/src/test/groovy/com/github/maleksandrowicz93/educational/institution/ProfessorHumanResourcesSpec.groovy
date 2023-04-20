package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.enums.EmploymentState.EMPLOYED
import static com.github.maleksandrowicz93.educational.institution.enums.EmploymentState.INACTIVE
import static com.github.maleksandrowicz93.educational.institution.result.reasons.ProfessorEmploymentApplicationFailureReason.FIELDS_OF_STUDY_NOT_MATCHED
import static com.github.maleksandrowicz93.educational.institution.result.reasons.ProfessorEmploymentApplicationFailureReason.NO_VACANCY
import static com.github.maleksandrowicz93.educational.institution.result.reasons.ProfessorEmploymentApplicationFailureReason.TOO_LITTLE_EXPERIENCE
import static com.github.maleksandrowicz93.educational.institution.result.reasons.ProfessorEmploymentResignationFailureReason.PROFESSOR_NOT_EMPLOYED
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.basicProfessorApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorApplication
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.MAIN_FIELD_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.NOT_KNOWN_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.SECONDARY_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.HumanResourcesUtils.newProfessorHumanResources

class ProfessorHumanResourcesSpec extends Specification {

    def "professor matching all requirements may be employed"() {
        given: "faculty with a vacancy"
        def snapshot = newProfessorHumanResources()
        def humanResources = ProfessorHumanResourcesAggregateRoot.from(snapshot)

        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(snapshot)

        when: "professor applies for employment"
        def result = humanResources.considerApplication(application)

        then: "professor should be employed"
        def currentSnapshot = humanResources.createSnapshot()
        currentSnapshot.id() == event.facultyId()
        def professors = currentSnapshot.professors()
        professors.size() == 1
        professors.any { it.employmentState() == EMPLOYED }

        and: "Professor Employed event should be created"
        result.value().isPresent()
        def event = result.value().get()
        with(event.professorSnapshot()) {
            professors.any { it.id() == id() }
            personalData() == application.personalData()
            employmentState() == EMPLOYED
        }
    }

    @Unroll("years of experience: #yearsOfExperience, fields of study: #fieldsOfStudy, failure reason: #resultReason.text()")
    def "professor not matching requirements should not be employed"() {
        given: "faculty with a vacancy"
        def snapshot = newProfessorHumanResources()
        def humanResources = ProfessorHumanResourcesAggregateRoot.from(snapshot)

        and: "professor's application with too little experience or not matched fields of study"
        def application = professorApplication(yearsOfExperience, fieldsOfStudy)

        when: "professor applies for employment"
        def result = humanResources.considerApplication(application)

        then: "professor should not be employed"
        def professors = humanResources.createSnapshot().professors()
        professors.isEmpty()

        and: "Professor Employed event should not be created"
        result.value().isEmpty()
        result.resultReason() == resultReason

        where:
        yearsOfExperience | fieldsOfStudy               | resultReason
        1                 | Set.of(MAIN_FIELD_OF_STUDY) | TOO_LITTLE_EXPERIENCE
        1                 | SECONDARY_FIELDS_OF_STUDY   | TOO_LITTLE_EXPERIENCE
        2                 | Set.of(MAIN_FIELD_OF_STUDY) | FIELDS_OF_STUDY_NOT_MATCHED
        2                 | NOT_KNOWN_FIELDS_OF_STUDY   | FIELDS_OF_STUDY_NOT_MATCHED
    }

    def "professor should not be employed when no vacancy at faculty"() {
        given: "faculty with no vacancy"
        def snapshot = newProfessorHumanResources().toBuilder()
                .professor(newProfessor())
                .professor(newProfessor())
                .build()
        def humanResources = ProfessorHumanResourcesAggregateRoot.from(snapshot)

        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(snapshot)

        when: "professor applies for employment"
        def result = humanResources.considerApplication(application)

        then: "professor should not be employed"
        def professors = humanResources.createSnapshot().professors()
        professors.isEmpty()

        and: "Professor Employed event should not be created"
        result.value().isEmpty()
        result.resultReason() == NO_VACANCY
    }

    def "professor may resign from employment"() {
        given: "faculty with employed professor"
        def professor = newProfessor()
        def snapshot = newProfessorHumanResources().toBuilder()
                .professor(professor)
                .build()
        def humanResources = ProfessorHumanResourcesAggregateRoot.from(snapshot)

        when: "professor resigns from employment"
        def result = humanResources.receiveResignation(professor.id())

        then: "employment resignation should be received correctly"
        def professors = humanResources.createSnapshot().professors()
        professors.size() == 1
        professors.any { it.id() == event.professorId() }

        and: "professor is marked as inactive"
        professors.any { it.employmentState() == INACTIVE }

        and: "Professor Resigned event should be created"
        result.value().isPresent()
        def event = result.value().get()
        event.facultyId() == snapshot.id()
        event.professorId() == professor.id()
    }

    def "professor cannot resign from employment when not employed at the faculty"() {
        given: "faculty with employed professor"
        def employed = newProfessor()
        def snapshot = newProfessorHumanResources().toBuilder()
                .professor(employed)
                .build()
        def humanResources = ProfessorHumanResourcesAggregateRoot.from(snapshot)

        and: "other professor not employed at the faculty"
        def otherProfessor = newProfessor()

        when: "professor resigns from employment foreign faculty"
        def result = humanResources.receiveResignation(otherProfessor.id())

        then: "employment resignation should fail"
        def professors = humanResources.createSnapshot().professors()
        !professors.any { it.employmentState() == INACTIVE }

        and: "Professor Resigned event should not be created"
        result.value().isEmpty()
        result.resultReason() == PROFESSOR_NOT_EMPLOYED
    }
}
