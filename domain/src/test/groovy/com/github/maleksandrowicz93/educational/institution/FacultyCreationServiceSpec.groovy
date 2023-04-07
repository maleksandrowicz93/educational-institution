package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.api.domain.service.FacultyCreationService
import com.github.maleksandrowicz93.educational.institution.reposiotry.EducationalInstitutionSimpleRepository
import com.github.maleksandrowicz93.educational.institution.reposiotry.FacultySimpleRepository
import com.github.maleksandrowicz93.educational.institution.repository.EducationalInstitutionRepository
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository
import com.github.maleksandrowicz93.educational.institution.results.FacultyCreationResultReason
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.EducationalInstitutionUtils.newEducationalInstitution
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.facultySetup

class FacultyCreationServiceSpec extends Specification {

    EducationalInstitutionRepository educationalInstitutionRepository
    FacultyRepository facultyRepository
    FacultyCreationService service

    def setup() {
        educationalInstitutionRepository = new EducationalInstitutionSimpleRepository()
        facultyRepository = new FacultySimpleRepository()
        service = new DomainFacultyCreationService(educationalInstitutionRepository, facultyRepository)
    }

    def "created faculty should be stored in proper repositories"() {
        given: "educational institution"
        def educationalInstitution = newEducationalInstitution()
        educationalInstitutionRepository.save(educationalInstitution)

        and: "faculty setup to be created"
        def facultySetup = facultySetup(educationalInstitution.id())

        when: "faculty is created"
        def facultyCreationResult = service.createFaculty(facultySetup)

        then: "should be created successfully"
        def maybeCreated = facultyCreationResult.value()
        maybeCreated.isPresent()
        def created = maybeCreated.get()

        and: "should be stored both in educational institution and faulty repositories"
        def maybeFaculties = educationalInstitutionRepository.findById(educationalInstitution.id())
                .map { it.faculties() }
        maybeFaculties.isPresent()
        maybeFaculties.get().contains(created.id())
        facultyRepository.existsById(created.id())
    }

    def "nothing should be stored in repository at faculty creation failure"() {
        given: "faculty setup to be created at not existing educational institution"
        def notKnownId = new EducationalInstitutionId(UUID.randomUUID())
        def facultySetup = facultySetup(notKnownId)

        when: "faculty is created"
        def facultyCreationResult = service.createFaculty(facultySetup)

        then: "should not be created successfully"
        facultyCreationResult.value().isEmpty()
        facultyCreationResult.resultReason() == FacultyCreationResultReason.INCORRECT_EDUCATIONAL_INSTITUTION_ID

        and: "should not be stored in any repository"
        !educationalInstitutionRepository.existsById(notKnownId)
    }
}
