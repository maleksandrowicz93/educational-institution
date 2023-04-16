package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.api.domain.service.FacultyManagementService
import com.github.maleksandrowicz93.educational.institution.events.ProfessorHiringResignationEvent
import com.github.maleksandrowicz93.educational.institution.events.StudentEnrollmentResignationEvent
import com.github.maleksandrowicz93.educational.institution.evetns.publisher.InMemoryEventsPublisher
import com.github.maleksandrowicz93.educational.institution.reposiotry.InMemoryFacultyRepository
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository
import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newStudent
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.newFaculty

class FacultyManagementServiceSpec extends Specification {

    FacultyRepository facultyRepository
    InMemoryEventsPublisher eventsPublisher
    FacultyManagementService service

    def setup() {
        facultyRepository = new InMemoryFacultyRepository()
        eventsPublisher = new InMemoryEventsPublisher()
        service = new DomainFacultyManagementService(facultyRepository, eventsPublisher)
    }

    def "after successful employment resignation notification should be sent"() {
        given: "faculty with hired professor"
        def newFaculty = newFaculty()
        def hired = newProfessor(newFaculty.id())
        def snapshot = newFaculty.toBuilder()
                .professor(hired)
                .build()
        facultyRepository.save(snapshot)

        when: "professor resigns from employment"
        def employmentResignationResult = service.resignFromEmployment(hired)

        then: "resignation should be received successfully"
        employmentResignationResult.value().isPresent()

        and: "faculty should be updated in repository"
        def maybeFaculty = facultyRepository.findById(snapshot.id())
        maybeFaculty.isPresent()
        def faculty = maybeFaculty.get()
        def maybeResigned = faculty.professors().stream()
                .filter { it.id() == hired.id() }
                .findFirst()
        maybeResigned.isPresent()
        def resigned = maybeResigned.get()

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new ProfessorHiringResignationEvent(resigned))
    }

    def "after unsuccessful employment resignation notification should not be sent"() {
        given: "professor hired at not known faculty"
        def notKnownFacultyId = new FacultyId(UUID.randomUUID())
        def notKnownProfessor = newProfessor(notKnownFacultyId)

        when: "professor resigns from employment"
        def employmentResignationResult = service.resignFromEmployment(notKnownProfessor)

        then: "resignation should not be received successfully"
        employmentResignationResult.value().isEmpty()
        employmentResignationResult.resultReason() == EmploymentResignationResultReason.INCORRECT_FACULTY_ID

        and: "should not be stored in any repository"
        !facultyRepository.existsById(notKnownFacultyId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }

    def "after successful enrollment resignation notification should be sent"() {
        given: "faculty with enrolled student"
        def newFaculty = newFaculty()
        def enrolled = newStudent(newFaculty.id())
        def faculty = newFaculty.toBuilder()
                .student(enrolled)
                .build()
        facultyRepository.save(faculty)

        when: "student resigns from enrollment"
        def enrollmentResignationResult = service.resignFromEnrollment(enrolled)

        then: "resignation should be received successfully"
        enrollmentResignationResult.value().isPresent()

        and: "faculty should be updated in repository"
        def maybeFaculty = facultyRepository.findById(faculty.id())
        maybeFaculty.isPresent()
        def course = maybeFaculty.get()
        def maybeResigned = course.students().stream()
                .filter { it.id() == enrolled.id() }
                .findFirst()
        maybeResigned.isPresent()
        def resigned = maybeResigned.get()

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new StudentEnrollmentResignationEvent(resigned))
    }

    def "after unsuccessful enrollment resignation notification should not be sent"() {
        given: "student enrolled at not known faculty"
        def notKnownFacultyId = new FacultyId(UUID.randomUUID())
        def notKnownStudent = newStudent(notKnownFacultyId)

        when: "student resigns from enrollment"
        def enrollmentResignationResult = service.resignFromEnrollment(notKnownStudent)

        then: "resignation should not be received successfully"
        enrollmentResignationResult.value().isEmpty()
        enrollmentResignationResult.resultReason() == EnrollmentResignationResultReason.INCORRECT_FACULTY_ID

        and: "should not be stored in any repository"
        !facultyRepository.existsById(notKnownFacultyId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }
}
