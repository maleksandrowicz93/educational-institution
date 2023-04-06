package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourses
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.basicCourseProposition
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.courseProposition
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.newFaculty
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.fieldsOfStudyFromNames
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.notMatchedFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.tooFewFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.tooManyFieldsOfStudy

class CourseCreatorSpec extends Specification {

    def "professor with capacity may create a course matching all requirements within a faculty"() {
        given: "faculty with hired professor with capacity"
        def newFaculty = newFaculty()
        def hired = newProfessor(newFaculty.id())
        def snapshot = newFaculty.toBuilder()
                .professor(hired)
                .build()
        def courseCreator = CourseCreatorModel.from(snapshot)

        and: "course matching all requirements to be created"
        def courseProposition = basicCourseProposition(hired, snapshot.id())

        when: "professor creates course"
        def courseCreationResult = courseCreator.considerCourseCreation(courseProposition)

        then: "course should be created"
        def courses = courseCreator.createSnapshot().courses()
        def maybeCreated = courseCreationResult.value()
        maybeCreated.isPresent()
        with(maybeCreated.get()) {
            id().value() != null
            courses.contains(id())
            name() == courseProposition.name()
            facultyId() == courseProposition.facultyId()
            professor().id() == courseProposition.professorId()
            fieldsOfStudy() == courseProposition.fieldsOfStudy()
            students().isEmpty()
            state() == CourseState.LED
        }
    }

    @Unroll("fields of study: #fieldsOfStudyNames, failure reason: #resultReason")
    def "professor with capacity should not create a course not matching requirements within a faculty"() {
        given: "faculty with hired professor with capacity"
        def newFaculty = newFaculty()
        def hired = newProfessor(newFaculty.id())
        def snapshot = newFaculty.toBuilder()
                .professor(hired)
                .build()
        def courseCreator = CourseCreatorModel.from(snapshot)

        and: "course not matching requirements to be created"
        def fieldsOfStudy = fieldsOfStudyFromNames(fieldsOfStudyNames, snapshot.id())
        def courseProposition = courseProposition(hired, fieldsOfStudy)

        when: "professor creates course"
        def courseCreationResult = courseCreator.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def courses = courseCreator.createSnapshot().courses()
        courses.isEmpty()
        courseCreationResult.value().isEmpty()
        courseCreationResult.resultReason() == resultReason

        where:
        fieldsOfStudyNames        | resultReason
        tooFewFieldsOfStudy()     | CourseCreationResultReason.TOO_FEW_FIELDS_OF_STUDY
        tooManyFieldsOfStudy()    | CourseCreationResultReason.TOO_MANY_FIELDS_OF_STUDY
        notMatchedFieldsOfStudy() | CourseCreationResultReason.PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED
    }

    def "professor with capacity should not create a course matching all requirements within a full faculty"() {
        given: "faculty with hired professor leading all faculty courses"
        def newFaculty = newFaculty()
        def courses = Set.of(new CourseId(UUID.randomUUID()), new CourseId(UUID.randomUUID()))
        def busyProfessor = professorLeadingCourses(newFaculty.id(), courses)
        def snapshotBuilder = newFaculty.toBuilder()
                .professor(busyProfessor)
                .courses(courses)

        and: "another hired professor with capacity"
        def freeProfessor = newProfessor(newFaculty.id())
        def snapshot = snapshotBuilder
                .professor(freeProfessor)
                .build()
        def courseCreator = CourseCreatorModel.from(snapshot)

        and: "new course to be created"
        def courseProposition = basicCourseProposition(freeProfessor, snapshot.id())

        when: "free professor creates the course"
        def courseCreationResult = courseCreator.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def currentCourses = courseCreator.createSnapshot().courses()
        currentCourses == courses
        courseCreationResult.value().isEmpty()
        courseCreationResult.resultReason() == CourseCreationResultReason.NO_CAPACITY_AT_FACULTY
    }

    def "professor with no capacity should not create a course matching all requirements within a faculty"() {
        given: "Educational Institution allowing maximum one led course per professor"
        def maximumProfessorCourses = new Threshold(1)
        def newFaculty = newFaculty(maximumProfessorCourses)
        def snapshotBuilder = newFaculty.toBuilder()

        and: "professor with no capacity hired at a faculty"
        def courses = Set.of(new CourseId(UUID.randomUUID()))
        def hired = professorLeadingCourses(newFaculty.id(), courses)
        def snapshot = snapshotBuilder
                .professor(hired)
                .courses(courses)
                .build()
        def courseCreator = CourseCreatorModel.from(snapshot)

        and: "course matching all requirements to be created"
        def newCourseProposition = basicCourseProposition(hired, snapshot.id())

        when: "professor creates course"
        def courseCreationResult = courseCreator.considerCourseCreation(newCourseProposition)

        then: "course should not be created"
        def currentCourses = courseCreator.createSnapshot().courses()
        currentCourses == courses
        courseCreationResult.value().isEmpty()
        courseCreationResult.resultReason() == CourseCreationResultReason.NO_PROFESSOR_CAPACITY
    }

    def "professor with should not create a course within faculty he is not hired at"() {
        given: "faculty with no professor"
        def snapshot = newFaculty()
        def courseCreator = CourseCreatorModel.from(snapshot)

        and: "professor hired at other faculty"
        def professor = newProfessor(new FacultyId(UUID.randomUUID()))

        and: "course proposition of this professor to be created at faculty with no hired professor"
        def courseProposition = basicCourseProposition(professor, snapshot.id())

        when: "professor creates course"
        def courseCreationResult = courseCreator.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def currentCourses = courseCreator.createSnapshot().courses()
        currentCourses.isEmpty()
        courseCreationResult.value().isEmpty()
        courseCreationResult.resultReason() == CourseCreationResultReason.INCORRECT_PROFESSOR_ID
    }
}
