package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.api.InMemoryEventsPublisher
import com.github.maleksandrowicz93.educational.institution.repository.InMemoryRectorOfficeRepository
import com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.api.DefaultResultReason.NOT_FOUND
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.getFACULTY_NAME
import static com.github.maleksandrowicz93.educational.institution.utils.RectorOfficeUtils.newRectorOffice

class RectorOfficeServiceSpec extends Specification {

    def rectorOfficeRepository = new InMemoryRectorOfficeRepository()
    def eventsPublisher = new InMemoryEventsPublisher()
    def rectorOfficeService = new RectorOfficeDomainService(rectorOfficeRepository, eventsPublisher)

    def "new faculty may be created"() {
        given: "rector office persisted in repository"
        def snapshot = newRectorOffice()
        def rectorOfficeId = snapshot.id()
        def rectorOffice = RectorOfficeAggregateRoot.from(snapshot)
        rectorOfficeRepository.save(rectorOffice)

        and: "faculty setup to be created"
        def facultySetup = FacultyUtils.facultySetup()

        when: "new faculty is created"
        def result = rectorOfficeService.createFaculty(rectorOfficeId, facultySetup)

        then: "Faculty Created Event should be published"
        result.value().isPresent()
        def event = result.value().get()
        eventsPublisher.events().contains(event)

        and: "repository should be updated with new faculty"
        def maybeRectorOffice = rectorOfficeRepository.findById(rectorOfficeId)
        maybeRectorOffice.isPresent()
        def currentRectorOffice = maybeRectorOffice.get()
        def currentSnapshot = currentRectorOffice.createSnapshot()
        def faculties = currentSnapshot.faculties()
        faculties.any { it.name() == facultySetup.name() }
    }

    def "faculty should not be created when rector office not found in system"() {
        given: "not existing rector office"
        def rectorOfficeId = new RectorOfficeId(UUID.randomUUID())

        and: "faculty setup to be created"
        def facultySetup = FacultyUtils.facultySetup()

        when: "new faculty is created"
        def result = rectorOfficeService.createFaculty(rectorOfficeId, facultySetup)

        then: "Faculty Created Event should be published"
        result.value().isEmpty()
        result.resultReason() == NOT_FOUND
        eventsPublisher.events().isEmpty()

        and: "repository should remains empty"
        rectorOfficeRepository.findById(rectorOfficeId).isEmpty()
    }

    def "faculty should not be created when any failure occurs"() {
        given: "rector office with a faculty persisted in repository"
        def snapshot = newRectorOffice().toBuilder()
                .faculty(FacultySnapshot.builder()
                        .id(new FacultyId(UUID.randomUUID()))
                        .name(FACULTY_NAME)
                        .build())
                .build()
        def rectorOfficeId = snapshot.id()
        def rectorOffice = RectorOfficeAggregateRoot.from(snapshot)
        rectorOfficeRepository.save(rectorOffice)

        and: "faculty setup with already taken name"
        def facultySetup = FacultyUtils.facultySetup()

        when: "new faculty is created"
        def result = rectorOfficeService.createFaculty(rectorOfficeId, facultySetup)

        then: "Faculty Created Event should be published"
        result.value().isEmpty()
        eventsPublisher.events().isEmpty()

        and: "repository should remains empty"
        def maybeRectorOffice = rectorOfficeRepository.findById(rectorOfficeId)
        maybeRectorOffice.isPresent()
        def currentRectorOffice = maybeRectorOffice.get()
        def currentSnapshot = currentRectorOffice.createSnapshot()
        def faculties = currentSnapshot.faculties()
        !faculties.any { it.name() == facultySetup.name() }
    }
}
