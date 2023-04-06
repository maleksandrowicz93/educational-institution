package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.basicStudentApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newStudent
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentEnrolledForCourses
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.newFaculty

class DeanOfficeSpec extends Specification {

    def "student matching all requirements may be enrolled"() {
        given: "faculty with a vacancy"
        def snapshot = newFaculty()
        def deanOffice = DeanOfficeModel.from(snapshot)

        and: "student's application matching all requirements"
        def application = basicStudentApplication(snapshot)

        when: "student applies for enrollment"
        def enrollmentResult = deanOffice.considerEnrollment(application)

        then: "student should be enrolled"
        def students = deanOffice.createSnapshot().students()
        def maybeEnrolled = enrollmentResult.value()
        maybeEnrolled.isPresent()
        with(maybeEnrolled.get()) {
            students.contains(it)
            id().value() != null
            personalData() == application.personalData()
            facultyId() == snapshot.id()
            enrollmentState() == EnrollmentState.ENROLLED
        }
    }

    @Unroll("main test result: #mainTestResult, secondary tests result: #secondaryTestResult, failure reason: #resultReason.text()")
    def "student not matching requirements should not be enrolled"() {
        given: "faculty with a vacancy"
        def snapshot = newFaculty()
        def deanOffice = DeanOfficeModel.from(snapshot)

        and: "student's application not matching requirements"
        def application = studentApplication(snapshot, mainTestResult, secondaryTestResult)

        when: "student applies for enrollment"
        def enrollmentResult = deanOffice.considerEnrollment(application)

        then: "student should not be enrolled"
        def students = deanOffice.createSnapshot().students()
        students.isEmpty()
        enrollmentResult.value().isEmpty()
        enrollmentResult.resultReason() == resultReason

        where:
        mainTestResult | secondaryTestResult | resultReason
        80             | 0                   | EnrollmentResultReason.SECONDARY_FIELDS_OF_STUDY_TESTS_NOT_PASSED
        0              | 80                  | EnrollmentResultReason.MAIN_FIELD_OF_STUDY_TEST_NOT_PASSED
        0              | 0                   | EnrollmentResultReason.MAIN_FIELD_OF_STUDY_TEST_NOT_PASSED
    }

    def "student should not be enrolled when no vacancy at faculty"() {
        given: "faculty with no vacancy"
        def newFaculty = newFaculty()
        def snapshot = newFaculty.toBuilder()
                .student(newStudent(newFaculty.id()))
                .student(newStudent(newFaculty.id()))
                .build()
        def deanOffice = DeanOfficeModel.from(snapshot)

        and: "student's application matching all requirements"
        def application = basicStudentApplication(snapshot)

        when: "student applies for enrollment"
        def enrollmentResult = deanOffice.considerEnrollment(application)

        then: "student should not be enrolled"
        def students = deanOffice.createSnapshot().students()
        students.isEmpty()
        enrollmentResult.value().isEmpty()
        enrollmentResult.resultReason() == EnrollmentResultReason.NO_VACANCY
    }

    def "student may resign from enrollment"() {
        given: "faculty with a student enrolled for 2 courses"
        def newFaculty = newFaculty()
        def courses = Set.of(new CourseId(UUID.randomUUID()), new CourseId(UUID.randomUUID()))
        def student = studentEnrolledForCourses(newFaculty.id(), courses)
        def snapshot = newFaculty.toBuilder()
                .student(student)
                .courses(courses)
                .build()
        def deanOffice = DeanOfficeModel.from(snapshot)

        when: "student resigns from enrollment"
        def enrollmentResignationResult = deanOffice.receiveEnrollmentResignation(student.id())

        then: "enrollment resignation should be received correctly"
        def maybeResigned = enrollmentResignationResult.value()
        maybeResigned.isPresent()

        and: "all courses student is enrolled for become vacated"
        def currentSnapshot = deanOffice.createSnapshot()
        def coursesStudentIsEnrolledFor = currentSnapshot.students().stream()
                .map { it.courses() }
                .flatMap { it.stream() }
                .collect()
        coursesStudentIsEnrolledFor.isEmpty()

        and: "student is marked as inactive"
        maybeResigned.get().enrollmentState() == EnrollmentState.INACTIVE
    }

    def "student cannot resign from enrollment at other faculty"() {
        given: "faculty with a student enrolled for 2 courses"
        def newFaculty = newFaculty()
        def courses = Set.of(new CourseId(UUID.randomUUID()), new CourseId(UUID.randomUUID()))
        def student = studentEnrolledForCourses(newFaculty.id(), courses)
        def snapshot = newFaculty.toBuilder()
                .student(student)
                .courses(courses)
                .build()
        def deanOffice = DeanOfficeModel.from(snapshot)

        and: "student enrolled at other faculty"
        def otherStudent = newStudent(new FacultyId(UUID.randomUUID()))

        when: "student resigns from enrollment"
        def enrollmentResignationResult = deanOffice.receiveEnrollmentResignation(otherStudent.id())

        then: "enrollment resignation should fail"
        enrollmentResignationResult.value().isEmpty()
        enrollmentResignationResult.resultReason() == EnrollmentResignationResultReason.INCORRECT_STUDENT_ID

        and: "faculty should stay the same"
        deanOffice.createSnapshot() == snapshot
    }
}
