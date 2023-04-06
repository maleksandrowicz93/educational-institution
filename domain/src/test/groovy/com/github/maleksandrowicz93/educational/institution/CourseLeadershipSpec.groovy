package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.results.CourseOvertakingResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.newCourse
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.fieldsOfStudyFromNames
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.notMatchedFieldsOfStudy

class CourseLeadershipSpec extends Specification {

    def "professor may resign from leading the course"() {
        given: "vacated course lead by professor"
        def newCourse = newCourse()
        def hired = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(hired)
                .build()
        def courseLeadership = CourseLeadershipModel.from(snapshot)

        when: "professor resigns from leading the course"
        def vacated = courseLeadership.receiveCourseLeadingResignation()

        then: "the course becomes vacated"
        vacated.professor() == null
        vacated.state() == CourseState.FREE
    }

    def "vacated course may be overtaken by a professor matching all requirements"() {
        given: "vacated course"
        def snapshot = newCourse().toBuilder()
                .state(CourseState.FREE)
                .build()
        def courseLeadership = CourseLeadershipModel.from(snapshot)

        and: "new professor matching all course requirements"
        def overtaking = newProfessor(snapshot.facultyId())

        when: "this professor overtakes the course"
        def courseOvertakingResult = courseLeadership.considerCourseOvertaking(overtaking)

        then: "course should be overtaken"
        def currentSnapshot = courseLeadership.createSnapshot()
        def maybeOvertaken = courseOvertakingResult.value()
        maybeOvertaken.isPresent()
        with(maybeOvertaken.get()) {
            it == currentSnapshot
            it.professor().id() == overtaking.id()
            it.state() == CourseState.LED
        }
    }

    def "vacated course should not be overtaken by a professor not matching requirements"() {
        given: "vacated course"
        def snapshot = newCourse().toBuilder()
                .state(CourseState.FREE)
                .build()
        def courseLeadership = CourseLeadershipModel.from(snapshot)

        and: "new professor not matching course requirements"
        def overtaking = newProfessor(snapshot.facultyId()).toBuilder()
                .fieldsOfStudy(fieldsOfStudyFromNames(notMatchedFieldsOfStudy(), snapshot.facultyId()))
                .build()

        when: "this professor overtakes the course"
        def courseOvertakingResult = courseLeadership.considerCourseOvertaking(overtaking)

        then: "course should not be overtaken"
        courseOvertakingResult.value().isEmpty()
        courseOvertakingResult.resultReason() == CourseOvertakingResultReason.PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.state() == CourseState.FREE
        currentSnapshot.professor() == null
    }

    def "vacated course should not be overtaken by a professor with no capacity"() {
        given: "vacated course"
        def snapshot = newCourse(new Threshold(1)).toBuilder()
                .state(CourseState.FREE)
                .build()
        def courseLeadership = CourseLeadershipModel.from(snapshot)

        and: "professor with no capacity matching all course requirements"
        def overtaking = newProfessor(snapshot.facultyId()).toBuilder()
                .ledCourse(new CourseId(UUID.randomUUID()))
                .build()

        when: "this professor overtakes the course"
        def courseOvertakingResult = courseLeadership.considerCourseOvertaking(overtaking)

        then: "course should not be overtaken"
        courseOvertakingResult.value().isEmpty()
        courseOvertakingResult.resultReason() == CourseOvertakingResultReason.NO_PROFESSOR_CAPACITY
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.state() == CourseState.FREE
        currentSnapshot.professor() == null
    }

    def "vacated course should not be overtaken by a professor from other faculty"() {
        given: "vacated course"
        def snapshot = newCourse().toBuilder()
                .state(CourseState.FREE)
                .build()
        def courseLeadership = CourseLeadershipModel.from(snapshot)

        and: "new professor matching all course requirements hired at another faculty"
        def overtaking = newProfessor(new FacultyId(UUID.randomUUID()))

        when: "professor overtakes the curse"
        def courseOvertakingResult = courseLeadership.considerCourseOvertaking(overtaking)

        then: "course should not be overtaken"
        courseOvertakingResult.value().isEmpty()
        courseOvertakingResult.resultReason() == CourseOvertakingResultReason.INVALID_FACULTY
        def currentSnapshot = courseLeadership.createSnapshot()
        currentSnapshot.state() == CourseState.FREE
        currentSnapshot.professor() == null
    }
}
