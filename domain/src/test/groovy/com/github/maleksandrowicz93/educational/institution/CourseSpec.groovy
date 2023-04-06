package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResultReason
import com.github.maleksandrowicz93.educational.institution.results.CourseEnrollmentResultReason
import com.github.maleksandrowicz93.educational.institution.results.CourseOvertakingResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newStudent
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentEnrolledForCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.newCourse
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.fieldsOfStudyFromNames
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.notMatchedFieldsOfStudy

class CourseSpec extends Specification {

    def "student may enroll for the vacated course"() {
        given: "vacated course"
        def facultyId = new FacultyId(UUID.randomUUID())
        def snapshot = newCourse()
        def course = Course.from(snapshot)

        and: "new student's to be enrolled for the course"
        def newStudent = newStudent(snapshot.facultyId())

        when: "student enrolls for the course"
        def courseEnrollmentResult = course.considerCourseEnrollment(newStudent)

        then: "student should be enrolled"
        def courseStudents = course.createSnapshot().students()
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
        def course = Course.from(snapshot)

        and: "new student's to be enrolled for the course"
        def newStudent = newStudent(newCourse.facultyId())

        when: "student enrolls for the course"
        def courseEnrollmentResult = course.considerCourseEnrollment(newStudent)

        then: "student should not be enrolled"
        def courseStudents = course.createSnapshot().students()
        !courseStudents.contains(newStudent)
        courseEnrollmentResult.value().isEmpty()
        courseEnrollmentResult.resultReason() == CourseEnrollmentResultReason.NO_VACANCY
    }

    def "student should not be enrolled for the vacated course from other faculty"() {
        given: "vacated course"
        def snapshot = newCourse()
        def course = Course.from(snapshot)

        and: "new student from other faculty to be enrolled"
        def newStudent = newStudent(new FacultyId(UUID.randomUUID()))

        when: "student enrolls for the course"
        def courseEnrollmentResult = course.considerCourseEnrollment(newStudent)

        then: "student should not be enrolled"
        def courseStudents = course.createSnapshot().students()
        !courseStudents.contains(newStudent)
        courseEnrollmentResult.value().isEmpty()
        courseEnrollmentResult.resultReason() == CourseEnrollmentResultReason.INVALID_FACULTY
    }

    def "course with too many vacancies may be closed"() {
        given: "vacated course lead by professor"
        def newCourse = newCourse()
        def hired = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def enrolled = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(hired)
                .student(enrolled)
                .build()
        def course = Course.from(snapshot)

        when: "professor closes the course"
        def courseClosingResult = course.considerClosingCourse()

        then: "the course should be closed"
        def currentSnapshot = course.createSnapshot()
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
        def course = Course.from(snapshot)

        when: "professor closes the course"
        def courseClosingResult = course.considerClosingCourse()

        then: "the course should not be closed"
        courseClosingResult.value().isEmpty()
        courseClosingResult.resultReason() == CourseClosingResultReason.TOO_MANY_STUDENTS
        with(course.createSnapshot()) {
            professor() == hired
            students().containsAll(Set.of(enrolled1, enrolled2))
            state() == CourseState.LED
        }
    }

    def "professor may resign from leading the course"() {
        given: "vacated course lead by professor"
        def newCourse = newCourse()
        def hired = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(hired)
                .build()
        def course = Course.from(snapshot)

        when: "professor resigns from leading the course"
        def vacated = course.receiveCourseLeadingResignation()

        then: "the course becomes vacated"
        vacated.professor() == null
        vacated.state() == CourseState.FREE
    }

    def "vacated course may be overtaken by a professor matching all requirements"() {
        given: "vacated course"
        def snapshot = newCourse().toBuilder()
                .state(CourseState.FREE)
                .build()
        def course = Course.from(snapshot)

        and: "new professor matching all course requirements"
        def overtaking = newProfessor(snapshot.facultyId())

        when: "this professor overtakes the course"
        def courseOvertakingResult = course.considerCourseOvertaking(overtaking)

        then: "course should be overtaken"
        def currentSnapshot = course.createSnapshot()
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
        def course = Course.from(snapshot)

        and: "new professor not matching course requirements"
        def overtaking = newProfessor(snapshot.facultyId()).toBuilder()
                .fieldsOfStudy(fieldsOfStudyFromNames(notMatchedFieldsOfStudy(), snapshot.facultyId()))
                .build()

        when: "this professor overtakes the course"
        def courseOvertakingResult = course.considerCourseOvertaking(overtaking)

        then: "course should not be overtaken"
        courseOvertakingResult.value().isEmpty()
        courseOvertakingResult.resultReason() == CourseOvertakingResultReason.PROFESSOR_FIELDS_OF_STUDY_NOT_MATCHED
        def currentSnapshot = course.createSnapshot()
        currentSnapshot.state() == CourseState.FREE
        currentSnapshot.professor() == null
    }

    def "vacated course should not be overtaken by a professor with no capacity"() {
        given: "vacated course"
        def snapshot = newCourse(new Threshold(1)).toBuilder()
                .state(CourseState.FREE)
                .build()
        def course = Course.from(snapshot)

        and: "professor with no capacity matching all course requirements"
        def overtaking = newProfessor(snapshot.facultyId()).toBuilder()
                .ledCourse(new CourseId(UUID.randomUUID()))
                .build()

        when: "this professor overtakes the course"
        def courseOvertakingResult = course.considerCourseOvertaking(overtaking)

        then: "course should not be overtaken"
        courseOvertakingResult.value().isEmpty()
        courseOvertakingResult.resultReason() == CourseOvertakingResultReason.NO_PROFESSOR_CAPACITY
        def currentSnapshot = course.createSnapshot()
        currentSnapshot.state() == CourseState.FREE
        currentSnapshot.professor() == null
    }

    def "vacated course should not be overtaken by a professor from other faculty"() {
        given: "vacated course"
        def snapshot = newCourse().toBuilder()
                .state(CourseState.FREE)
                .build()
        def course = Course.from(snapshot)

        and: "new professor matching all course requirements hired at another faculty"
        def overtaking = newProfessor(new FacultyId(UUID.randomUUID()))

        when: "professor overtakes the curse"
        def courseOvertakingResult = course.considerCourseOvertaking(overtaking)

        then: "course should not be overtaken"
        courseOvertakingResult.value().isEmpty()
        courseOvertakingResult.resultReason() == CourseOvertakingResultReason.INVALID_FACULTY
        def currentSnapshot = course.createSnapshot()
        currentSnapshot.state() == CourseState.FREE
        currentSnapshot.professor() == null
    }
}
