package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.api.domain.service.CourseManagementService
import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.events.CourseClosingEvent
import com.github.maleksandrowicz93.educational.institution.events.LeadingCourseResignationEvent
import com.github.maleksandrowicz93.educational.institution.api.InMemoryEventsPublisher
import com.github.maleksandrowicz93.educational.institution.reposiotry.InMemoryCourseRepository
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository
import com.github.maleksandrowicz93.educational.institution.result.reasons.CourseClosingResultReason
import com.github.maleksandrowicz93.educational.institution.result.reasons.CourseLeadingResignationResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentEnrolledForCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.newCourse

class CourseManagementServiceSpec extends Specification {

    CourseRepository courseRepository
    InMemoryEventsPublisher eventsPublisher
    CourseManagementService service

    def setup() {
        courseRepository = new InMemoryCourseRepository()
        eventsPublisher = new InMemoryEventsPublisher()
        service = new DomainCourseManagementService(courseRepository, eventsPublisher)
    }

    def "after successful course leading resignation notification should be sent"() {
        given: "course led by professor"
        def newCourse = newCourse()
        def professor = newProfessor(newCourse.facultyId())
        def course = newCourse.toBuilder()
                .professor(professor)
                .build()
        courseRepository.save(course)

        when: "professor resigns from leading course"
        def employmentResignationResult = service.resignFromLeadingCourse(course.id())

        then: "resignation should be received successfully"
        employmentResignationResult.value().isPresent()

        and: "course should be updated in repository"
        def maybeNotLedCourse = courseRepository.findById(course.id())
        maybeNotLedCourse.isPresent()
        def notLedCourse = maybeNotLedCourse.get()
        notLedCourse.professor() == null

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new LeadingCourseResignationEvent(notLedCourse))
    }

    def "after unsuccessful course leading resignation notification should not be sent"() {
        given: "course led by professor"
        def freeCourse = newCourse()
        def professor = newProfessor(freeCourse.facultyId())
        def ledCourse = freeCourse.toBuilder()
                .professor(professor)
                .build()
        courseRepository.save(ledCourse)

        and: "not known course"
        def notKnownId = new CourseId(UUID.randomUUID())

        when: "professor resigns from leading other course"
        def courseLeadingResignationResult = service.resignFromLeadingCourse(notKnownId)

        then: "resignation should not be received successfully"
        courseLeadingResignationResult.value().isEmpty()
        courseLeadingResignationResult.resultReason() == CourseLeadingResignationResultReason.INCORRECT_COURSE_ID

        and: "should not be stored in any repository"
        !courseRepository.existsById(notKnownId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }

    def "after successful course closing notification should be sent"() {
        given: "course led by professor with enrolled students"
        def newCourse = newCourse()
        def professor = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def student1 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def student2 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(professor)
                .student(student1)
                .student(student2)
                .build()
        courseRepository.save(snapshot)

        when: "professor closes the course"
        def courseClosingResult = service.closeCourse(snapshot.id())

        then: "course should be closed successfully"
        courseClosingResult.value().isPresent()

        and: "course should be updated in repository"
        def maybeClosed = courseRepository.findById(snapshot.id())
        maybeClosed.isPresent()
        def closed = maybeClosed.get()
        closed.professor() == null
        closed.state() == CourseState.CLOSED

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new CourseClosingEvent(closed))
    }

    def "after unsuccessful course closing notification should not be sent"() {
        given: "course led by professor with enrolled students"
        def newCourse = newCourse()
        def professor = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def student1 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def student2 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(professor)
                .student(student1)
                .student(student2)
                .build()
        courseRepository.save(snapshot)

        and: "not known other course"
        def notKnownId = new CourseId(UUID.randomUUID())

        when: "professors closes the other course"
        def courseClosingResult = service.closeCourse(notKnownId)

        then: "course should not be closed successfully"
        courseClosingResult.value().isEmpty()
        courseClosingResult.resultReason() == CourseClosingResultReason.INCORRECT_COURSE_ID

        and: "should not be stored in any repository"
        !courseRepository.existsById(notKnownId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }
}
