package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResultReason
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentEnrolledForCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.newCourse

class CourseCloserSpec extends Specification {

    def "course with too many vacancies may be closed"() {
        given: "vacated course lead by professor"
        def newCourse = newCourse()
        def hired = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def enrolled = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(hired)
                .student(enrolled)
                .build()
        def courseCloser = CourseCloserModel.from(snapshot)

        when: "professor closes the course"
        def courseClosingResult = courseCloser.considerClosingCourse()

        then: "the course should be closed"
        def currentSnapshot = courseCloser.createSnapshot()
        def maybeClosed = courseClosingResult.value()
        maybeClosed.isPresent()
        with(maybeClosed.get()) {
            it == currentSnapshot
            professor() == null
            students().isEmpty()
            state() == CourseState.CLOSED
        }
    }

    def "course with enough number of enrolled students should not be closed"() {
        given: "course with no vacancy"
        def newCourse = newCourse()
        def hired = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def enrolled1 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def enrolled2 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(hired)
                .student(enrolled1)
                .student(enrolled2)
                .build()
        def courseCloser = CourseCloserModel.from(snapshot)

        when: "professor closes the course"
        def courseClosingResult = courseCloser.considerClosingCourse()

        then: "the course should not be closed"
        courseClosingResult.value().isEmpty()
        courseClosingResult.resultReason() == CourseClosingResultReason.TOO_MANY_STUDENTS
        with(courseCloser.createSnapshot()) {
            professor() == hired
            students().containsAll(Set.of(enrolled1, enrolled2))
            state() == CourseState.LED
        }
    }
}
