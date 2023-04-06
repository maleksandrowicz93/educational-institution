package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResultReason
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newStudent
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentEnrolledForCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.newCourse

class CourseEnrollmentsRegistrySpec extends Specification {

    def "student may enroll for the vacated course"() {
        given: "vacated course"
        def facultyId = new FacultyId(UUID.randomUUID())
        def snapshot = newCourse()
        def courseEnrollmentsRegistry = CourseEnrollmentsRegistryModel.from(snapshot)

        and: "new student's to be enrolled for the course"
        def newStudent = newStudent(snapshot.facultyId())

        when: "student enrolls for the course"
        def courseEnrollmentResult = courseEnrollmentsRegistry.considerCourseEnrollment(newStudent)

        then: "student should be enrolled"
        def courseStudents = courseEnrollmentsRegistry.createSnapshot().students()
        def maybeEnrolled = courseEnrollmentResult.value()
        maybeEnrolled.isPresent()
        with(maybeEnrolled.get()) {
            id() == newStudent.id()
            facultyId() == newStudent.facultyId()
            personalData() == newStudent.personalData()
            courses().contains(snapshot.id())
            enrollmentState() == EnrollmentState.ENROLLED
            courseStudents.contains(it)
        }
    }

    def "student should not be enrolled for the full course"() {
        given: "course with no vacancy"
        def newCourse = newCourse()
        def enrolled1 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def enrolled2 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .student(enrolled1)
                .student(enrolled2)
                .build()
        def courseEnrollmentsRegistry = CourseEnrollmentsRegistryModel.from(snapshot)

        and: "new student's to be enrolled for the course"
        def newStudent = newStudent(newCourse.facultyId())

        when: "student enrolls for the course"
        def courseEnrollmentResult = courseEnrollmentsRegistry.considerCourseEnrollment(newStudent)

        then: "student should not be enrolled"
        def courseStudents = courseEnrollmentsRegistry.createSnapshot().students()
        !courseStudents.contains(newStudent)
        courseEnrollmentResult.value().isEmpty()
        courseEnrollmentResult.resultReason() == CourseEnrollmentResultReason.NO_VACANCY
    }

    def "student should not be enrolled for the vacated course from other faculty"() {
        given: "vacated course"
        def snapshot = newCourse()
        def courseEnrollmentsRegistry = CourseEnrollmentsRegistryModel.from(snapshot)

        and: "new student from other faculty to be enrolled"
        def newStudent = newStudent(new FacultyId(UUID.randomUUID()))

        when: "student enrolls for the course"
        def courseEnrollmentResult = courseEnrollmentsRegistry.considerCourseEnrollment(newStudent)

        then: "student should not be enrolled"
        def courseStudents = courseEnrollmentsRegistry.createSnapshot().students()
        !courseStudents.contains(newStudent)
        courseEnrollmentResult.value().isEmpty()
        courseEnrollmentResult.resultReason() == CourseEnrollmentResultReason.INVALID_FACULTY
    }
}
