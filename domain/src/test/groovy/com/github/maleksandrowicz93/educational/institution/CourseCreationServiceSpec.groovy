package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.api.business.CourseCreationService
import com.github.maleksandrowicz93.educational.institution.reposiotry.CourseSimpleRepository
import com.github.maleksandrowicz93.educational.institution.reposiotry.FacultySimpleRepository
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResultReason
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.basicCourseProposition
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.newFaculty

class CourseCreationServiceSpec extends Specification {

    FacultyRepository facultyRepository
    CourseRepository courseRepository
    CourseCreationService service

    def setup() {
        facultyRepository = new FacultySimpleRepository()
        courseRepository = new CourseSimpleRepository()
        service = new DomainCourseCreationService(facultyRepository, courseRepository)
    }

    def "created course should be stored in proper repositories"() {
        given: "faculty with hired professor"
        def newFaculty = newFaculty()
        def hired = newProfessor(newFaculty.id())
        def faculty = newFaculty.toBuilder()
                .professor(hired)
                .build()
        facultyRepository.save(faculty)

        and: "course proposition to be created"
        def courseProposition = basicCourseProposition(hired, faculty.id())

        when: "course is created"
        def courseCreationResult = service.createCourse(courseProposition)

        then: "should be created successfully"
        def maybeCreated = courseCreationResult.value()
        maybeCreated.isPresent()
        def created = maybeCreated.get()

        and: "should be stored both in faulty and course repositories"
        def maybeCourses = facultyRepository.findById(faculty.id())
                .map { it.courses() }
        maybeCourses.isPresent()
        maybeCourses.get().contains(created.id())
        courseRepository.existsById(created.id())
    }

    def "nothing should be stored in repository at course creation failure"() {
        given: "course proposition to be created at not existing faculty"
        def notKnownFacultyId = new FacultyId(UUID.randomUUID())
        def notKnownProfessor = newProfessor(notKnownFacultyId)
        def courseProposition = basicCourseProposition(notKnownProfessor, notKnownFacultyId)

        when: "course is created"
        def facultyCreationResult = service.createCourse(courseProposition)

        then: "should not be created successfully"
        facultyCreationResult.value().isEmpty()
        facultyCreationResult.resultReason() == CourseCreationResultReason.INCORRECT_FACULTY_ID

        and: "should not be stored in any repository"
        !facultyRepository.existsById(notKnownFacultyId)
    }
}
