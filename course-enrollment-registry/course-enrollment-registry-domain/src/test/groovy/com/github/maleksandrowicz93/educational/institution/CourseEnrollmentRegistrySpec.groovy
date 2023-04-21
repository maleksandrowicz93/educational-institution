package com.github.maleksandrowicz93.educational.institution


import com.github.maleksandrowicz93.educational.institution.vo.StudentId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.enums.CourseState.CLOSED
import static com.github.maleksandrowicz93.educational.institution.enums.CourseState.OPEN
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseClosingFailureReason.ALREADY_CLOSED
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseClosingFailureReason.TOO_MANY_STUDENTS
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseEnrollmentFailureReason.ALREADY_ENROLLED
import static com.github.maleksandrowicz93.educational.institution.result.reasons.CourseEnrollmentFailureReason.NO_VACANCY
import static com.github.maleksandrowicz93.educational.institution.utils.CourseEnrollmentRegistryUtils.closedCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseEnrollmentRegistryUtils.fullCourseRegistry
import static com.github.maleksandrowicz93.educational.institution.utils.CourseEnrollmentRegistryUtils.newCourseRegistry

class CourseEnrollmentRegistrySpec extends Specification {

    def "student may enroll for the vacated course"() {
        given: "vacated course"
        def snapshot = newCourseRegistry()
        def enrollmentsRegistry = CourseEnrollmentRegistryAggregateRoot.from(snapshot)

        and: "new student's to be enrolled for the course"
        def studentId = new StudentId(UUID.randomUUID())

        when: "student enrolls for the course"
        def result = enrollmentsRegistry.considerCourseEnrollment(studentId)

        then: "student should be enrolled"
        def currentSnapshot = enrollmentsRegistry.createSnapshot()
        def students = currentSnapshot.students()
        students.contains(studentId)

        and: "Student Enrolled For Course event should be created"
        result.value().isPresent()
        def event = result.value().get()
        event.courseId() == currentSnapshot.id()
        event.studentId() == studentId
    }

    def "student should not be enrolled for the full course"() {
        given: "full course"
        def snapshot = fullCourseRegistry()
        def enrollmentsRegistry = CourseEnrollmentRegistryAggregateRoot.from(snapshot)

        and: "new student's to be enrolled for the course"
        def studentId = new StudentId(UUID.randomUUID())

        when: "student enrolls for the course"
        def result = enrollmentsRegistry.considerCourseEnrollment(studentId)

        then: "student should not be enrolled"
        def currentSnapshot = enrollmentsRegistry.createSnapshot()
        def students = currentSnapshot.students()
        !students.contains(studentId)

        and: "Student Enrolled For Course event should not be created"
        result.value().isEmpty()
        result.resultReason() == NO_VACANCY
    }

    def "student should not be enrolled for the vacated course second time"() {
        given: "course with enrolled student"
        def studentId = new StudentId(UUID.randomUUID())
        def snapshot = newCourseRegistry().toBuilder()
                .student(studentId)
                .build()
        def enrollmentsRegistry = CourseEnrollmentRegistryAggregateRoot.from(snapshot)

        when: "student enrolls for the course second time"
        def result = enrollmentsRegistry.considerCourseEnrollment(studentId)

        then: "student should be still enrolled"
        def currentSnapshot = enrollmentsRegistry.createSnapshot()
        def students = currentSnapshot.students()
        students.contains(studentId)

        and: "students number should remains the same"
        students.size() == snapshot.students().size()

        and: "Student Enrolled For Course event should not be created"
        result.value().isEmpty()
        result.resultReason() == ALREADY_ENROLLED
    }

    def "course with too many vacancies may be closed"() {
        given: "course with too many vacancies"
        def snapshot = newCourseRegistry()
        def enrollmentsRegistry = CourseEnrollmentRegistryAggregateRoot.from(snapshot)

        when: "professor closes the course"
        def result = enrollmentsRegistry.considerClosingCourse()

        then: "the course should be closed"
        def currentSnapshot = enrollmentsRegistry.createSnapshot()
        currentSnapshot.courseState() == CLOSED

        and: "Course Closed event should be created"
        result.value().isPresent()
        def event = result.value().get()
        event.courseId() == currentSnapshot.id()
    }

    def "course with enough number of enrolled students should not be closed"() {
        given: "course with too many vacancies"
        def snapshot = fullCourseRegistry()
        def enrollmentsRegistry = CourseEnrollmentRegistryAggregateRoot.from(snapshot)

        when: "professor closes the course"
        def result = enrollmentsRegistry.considerClosingCourse()

        then: "the course should not be closed"
        def currentSnapshot = enrollmentsRegistry.createSnapshot()
        currentSnapshot.courseState() == OPEN

        and: "Course Closed event should not be created"
        result.value().isEmpty()
        result.resultReason() == TOO_MANY_STUDENTS
    }

    def "already closed course should not be closed"() {
        given: "closed course"
        def snapshot = closedCourse()
        def enrollmentsRegistry = CourseEnrollmentRegistryAggregateRoot.from(snapshot)

        when: "professor closes the course"
        def result = enrollmentsRegistry.considerClosingCourse()

        then: "course should remains closed"
        def currentSnapshot = enrollmentsRegistry.createSnapshot()
        currentSnapshot.courseState() == CLOSED

        and: "Course Closed event should not be created"
        result.value().isEmpty()
        result.resultReason() == ALREADY_CLOSED
    }
}
