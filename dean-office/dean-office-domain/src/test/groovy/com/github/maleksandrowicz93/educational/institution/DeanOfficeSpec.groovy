package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseCreationFailureReason.INCORRECT_PROFESSOR_ID
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseCreationFailureReason.NO_CAPACITY_AT_FACULTY
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseCreationFailureReason.NO_PROFESSOR_CAPACITY
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseCreationFailureReason.PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseCreationFailureReason.TOO_FEW_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseCreationFailureReason.TOO_MANY_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.courseProposition
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.newCourse
import static com.github.maleksandrowicz93.educational.institution.utils.DeanOfficeUtils.newDeanOffice
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.notMatchedFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.tooFewFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.tooManyFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.ProfessorUtils.newProfessor

class DeanOfficeSpec extends Specification {

    def "professor with capacity may create a course matching all requirements within a faculty"() {
        given: "faculty with employed professor with capacity"
        def professor = newProfessor()
        def snapshot = newDeanOffice().toBuilder()
                .professor(professor)
                .build()
        def deanOffice = DeanOfficeAggregateRoot.from(snapshot)

        and: "course proposition matching all requirements to be created"
        def courseProposition = courseProposition(professor.id())

        when: "professor creates the course"
        def result = deanOffice.considerCourseCreation(courseProposition)

        then: "course should be successfully created"
        def currentSnapshot = deanOffice.createSnapshot()
        def courses = currentSnapshot.courses()
        courses.size() == 1
        result.value().isPresent()

        and: "Course Created event should be created"
        def event = result.value().get()
        event.facultyId() == currentSnapshot.id()
        with(event.courseSnapshot()) {
            id().value() != null
            courses.any { it.id() == id() }
            name() == courseProposition.name()
            leadingProfessor() == courseProposition.leadingProfessor()
            fieldsOfStudy() == courseProposition.fieldsOfStudy()
        }
    }

    @Unroll("fields of study: #fieldsOfStudy, failure reason: #resultReason")
    def "professor with capacity should not create a course not matching his fields of study within a faculty"() {
        given: "faculty with employed professor with capacity"
        def professor = newProfessor()
        def snapshot = newDeanOffice().toBuilder()
                .professor(professor)
                .build()
        def deanOffice = DeanOfficeAggregateRoot.from(snapshot)

        and: "course proposition not matching professor's fields of study to be created"
        def courseProposition = courseProposition(professor.id(), fieldsOfStudy)

        when: "professor  the course"
        def courseCreationResult = deanOffice.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def courses = deanOffice.createSnapshot().courses()
        courses.isEmpty()

        and: "Course Created event shout not be created"
        courseCreationResult.value().isEmpty()
        courseCreationResult.resultReason() == resultReason

        where:
        fieldsOfStudy             | resultReason
        tooFewFieldsOfStudy()     | TOO_FEW_FIELDS_OF_STUDY
        tooManyFieldsOfStudy()    | TOO_MANY_FIELDS_OF_STUDY
        notMatchedFieldsOfStudy() | PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED
    }

    def "professor with capacity should not create a course matching all requirements within a full faculty"() {
        given: "full faculty with employed professor leading all faculty courses"
        def busyProfessor = newProfessor()
        def snapshotBuilder = newDeanOffice().toBuilder()
                .professor(busyProfessor)
                .course(newCourse(busyProfessor))
                .course(newCourse(busyProfessor))

        and: "another employed professor with capacity"
        def freeProfessor = newProfessor()
        def snapshot = snapshotBuilder
                .professor(freeProfessor)
                .build()
        def deanOffice = DeanOfficeAggregateRoot.from(snapshot)

        and: "new course proposition matching all requirements to be created"
        def courseProposition = courseProposition(freeProfessor.id())

        when: "free professor creates the course"
        def result = deanOffice.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def currentSnapshot = deanOffice.createSnapshot()
        currentSnapshot.courses().size() == snapshot.courses().size()

        and: "Course Created event shout not be created"
        result.value().isEmpty()
        result.resultReason() == NO_CAPACITY_AT_FACULTY
    }

    def "professor with no capacity should not create a course matching all requirements within a faculty"() {
        given: "Faculty with employed professor without capacity"
        def maximumProfessorCourses = new Threshold(1)
        def professor = newProfessor()
        def snapshot = newDeanOffice(maximumProfessorCourses).toBuilder()
                .professor(professor)
                .course(newCourse(professor))
                .build()
        def deanOffice = DeanOfficeAggregateRoot.from(snapshot)

        and: "course proposition matching all requirements to be created"
        def newCourseProposition = courseProposition(professor.id())

        when: "professor creates the course"
        def result = deanOffice.considerCourseCreation(newCourseProposition)

        then: "course should not be created"
        def currentSnapshot = deanOffice.createSnapshot()
        currentSnapshot.courses().size() == snapshot.courses().size()

        and: "Course Created event shout not be created"
        result.value().isEmpty()
        result.resultReason() == NO_PROFESSOR_CAPACITY
    }

    def "not employed professor should not create a course within the faculty"() {
        given: "faculty with no employed professor"
        def snapshot = newDeanOffice()
        def deanOffice = DeanOfficeAggregateRoot.from(snapshot)

        and: "not employed professor"
        def professor = newProfessor()

        and: "course proposition to be created"
        def courseProposition = courseProposition(professor.id())

        when: "professor creates the course"
        def result = deanOffice.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def currentCourses = deanOffice.createSnapshot().courses()
        currentCourses.isEmpty()

        and: "Course Created event shout not be created"
        result.value().isEmpty()
        result.resultReason() == INCORRECT_PROFESSOR_ID
    }
}
