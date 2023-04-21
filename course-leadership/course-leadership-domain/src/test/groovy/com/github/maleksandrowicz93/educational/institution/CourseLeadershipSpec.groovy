package com.github.maleksandrowicz93.educational.institution


import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.enums.CourseState.FREE
import static com.github.maleksandrowicz93.educational.institution.enums.CourseState.LED
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseLeadingResignationFailureReason.COURSE_ALREADY_FREE
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseOvertakingFailureReason.LEAD_BY_OTHER_PROFESSOR
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseOvertakingFailureReason.NO_PROFESSOR_CAPACITY
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseOvertakingFailureReason.PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.courseOvertakingApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.courseOvertakingApplicationWithNoCapacity
import static com.github.maleksandrowicz93.educational.institution.utils.CourseLeadershipUtils.freeCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseLeadershipUtils.ledCourse
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.notMatchedFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.tooFewFieldsOfStudy

class CourseLeadershipSpec extends Specification {

    def "professor may resign from leading the course"() {
        given: "course lead by professor"
        def professorId = new ProfessorId(UUID.randomUUID())
        def snapshot = ledCourse(professorId)
        def courseLeadership = CourseLeadershipAggregateRoot.from(snapshot)

        when: "professor resigns from leading the course"
        def result = courseLeadership.receiveCourseLeadingResignation()

        then: "the course becomes vacated"
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.leadingProfessor() == null
        currentSnapshot.courseState() == FREE

        and: "Course Become Free event should be created"
        result.value().isPresent()
        def event = result.value().get()
        event.courseId() == currentSnapshot.id()
    }

    def "professor should not resign from leading the course which is not led"() {
        given: "course lead by professor"
        def snapshot = freeCourse()
        def courseLeadership = CourseLeadershipAggregateRoot.from(snapshot)

        when: "professor resigns from leading the course"
        def result = courseLeadership.receiveCourseLeadingResignation()

        then: "the course remains free"
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.leadingProfessor() == null
        currentSnapshot.courseState() == FREE

        and: "Course Become Free event should not be created"
        result.value().isEmpty()
        result.resultReason() == COURSE_ALREADY_FREE
    }

    def "vacated course may be overtaken by a professor matching all requirements"() {
        given: "vacated course"
        def snapshot = freeCourse()
        def courseLeadership = CourseLeadershipAggregateRoot.from(snapshot)

        and: "professor's application for course overtaking matching all requirements"
        def professorId = new ProfessorId(UUID.randomUUID())
        def application = courseOvertakingApplication(professorId)

        when: "this professor overtakes the course"
        def result = courseLeadership.considerCourseOvertaking(application)

        then: "course should be overtaken"
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.leadingProfessor() == professorId
        currentSnapshot.courseState() == LED

        and: "Course Overtaken event should be created"
        result.value().ifPresent {}
        def event = result.value().get()
        event.courseId() == currentSnapshot.id()
        event.professorId() == professorId
    }

    @Unroll("fields of study: #fieldsOfStudy")
    def "vacated course should not be overtaken by a professor not matching course's fields of study"() {
        given: "vacated course"
        def snapshot = freeCourse()
        def courseLeadership = CourseLeadershipAggregateRoot.from(snapshot)

        and: "professor's application for course overtaking not matching requirements"
        def professorId = new ProfessorId(UUID.randomUUID())
        def application = courseOvertakingApplication(professorId, fieldsOfStudy)

        when: "this professor overtakes the course"
        def result = courseLeadership.considerCourseOvertaking(application)

        then: "course should not be overtaken"
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.leadingProfessor() == null
        currentSnapshot.courseState() == FREE

        and: "Course Overtaken event should be created"
        result.value().isEmpty()
        result.resultReason() == PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED

        where:
        fieldsOfStudyIds << [notMatchedFieldsOfStudy(), tooFewFieldsOfStudy()]
        fieldsOfStudy = fieldsOfStudyIds as Set<FieldOfStudyId>
    }

    def "vacated course should not be overtaken by a professor with no capacity"() {
        given: "vacated course"
        def snapshot = freeCourse()
        def courseLeadership = CourseLeadershipAggregateRoot.from(snapshot)

        and: "professor's application for course overtaking not matching requirements"
        def professorId = new ProfessorId(UUID.randomUUID())
        def application = courseOvertakingApplicationWithNoCapacity(professorId)

        when: "this professor overtakes the course"
        def result = courseLeadership.considerCourseOvertaking(application)

        then: "course should not be overtaken"
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.leadingProfessor() == null
        currentSnapshot.courseState() == FREE

        and: "Course Overtaken event should be created"
        result.value().isEmpty()
        result.resultReason() == NO_PROFESSOR_CAPACITY
    }

    def "vacated course should not be overtaken by a professor from other faculty"() {
        given: "course led by professor"
        def leadingProfessor = new ProfessorId(UUID.randomUUID())
        def snapshot = ledCourse(leadingProfessor)
        def courseLeadership = CourseLeadershipAggregateRoot.from(snapshot)

        and: "other professor's application for course overtaking matching all requirements"
        def otherProfessor = new ProfessorId(UUID.randomUUID())
        def application = courseOvertakingApplication(otherProfessor)

        when: "professor overtakes the curse"
        def result = courseLeadership.considerCourseOvertaking(application)

        then: "course should not be overtaken"
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.leadingProfessor() == leadingProfessor
        currentSnapshot.courseState() == LED

        and: "Course Overtaken event should be created"
        result.value().isEmpty()
        result.resultReason() == LEAD_BY_OTHER_PROFESSOR
    }
}
