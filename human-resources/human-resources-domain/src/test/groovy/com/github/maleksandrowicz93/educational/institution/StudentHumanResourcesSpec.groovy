package com.github.maleksandrowicz93.educational.institution

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState.ENROLLED
import static com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState.INACTIVE
import static com.github.maleksandrowicz93.educational.institution.result.reasons.StudentEnrollmentApplicationFailureReason.MAIN_FIELD_OF_STUDY_TEST_NOT_PASSED
import static com.github.maleksandrowicz93.educational.institution.result.reasons.StudentEnrollmentApplicationFailureReason.NO_VACANCY
import static com.github.maleksandrowicz93.educational.institution.result.reasons.StudentEnrollmentApplicationFailureReason.SECONDARY_FIELDS_OF_STUDY_TESTS_NOT_PASSED
import static com.github.maleksandrowicz93.educational.institution.result.reasons.StudentEnrollmentResignationResultReason.STUDENT_NOT_ENROLLED
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.basicStudentApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newStudent
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentApplication
import static com.github.maleksandrowicz93.educational.institution.utils.HumanResourcesUtils.newStudentHumanResources

class StudentHumanResourcesSpec extends Specification {

    def "student matching all requirements may be enrolled"() {
        given: "faculty with a vacancy"
        def snapshot = newStudentHumanResources()
        def humanResources = StudentHumanResourcesAggregateRoot.from(snapshot)

        and: "student's application matching all requirements"
        def application = basicStudentApplication(snapshot)

        when: "student applies for enrollment"
        def result = humanResources.considerApplication(application)

        then: "student should be enrolled"
        def currentSnapshot = humanResources.createSnapshot()
        def professors = currentSnapshot.students()
        professors.size() == 1
        professors.any { it.enrollmentState() == ENROLLED }

        and: "Student Enrolled event should be created"
        result.value().isPresent()
        def event = result.value().get()
        currentSnapshot.id() == event.facultyId()
        with(event.studentSnapshot()) {
            professors.any { it.id() == id() }
            personalData() == application.personalData()
            enrollmentState() == ENROLLED
        }
    }

    @Unroll("main test result: #mainTestResult, secondary tests result: #secondaryTestResult, failure reason: #resultReason.text()")
    def "student not matching requirements should not be enrolled"() {
        given: "faculty with a vacancy"
        def snapshot = newStudentHumanResources()
        def humanResources = StudentHumanResourcesAggregateRoot.from(snapshot)

        and: "student's application with too low exams scores"
        def application = studentApplication(snapshot, mainTestResult, secondaryTestResult)

        when: "student applies for enrollment"
        def result = humanResources.considerApplication(application)

        then: "student should not be enrolled"
        def students = humanResources.createSnapshot().students()
        students.isEmpty()

        and: "Student Enrolled event should not be created"
        result.value().isEmpty()
        result.resultReason() == resultReason

        where:
        mainTestResult | secondaryTestResult | resultReason
        80             | 0                   | SECONDARY_FIELDS_OF_STUDY_TESTS_NOT_PASSED
        0              | 80                  | MAIN_FIELD_OF_STUDY_TEST_NOT_PASSED
        0              | 0                   | MAIN_FIELD_OF_STUDY_TEST_NOT_PASSED
    }

    def "student should not be enrolled when no vacancy at faculty"() {
        given: "faculty with no vacancy"
        def snapshot = newStudentHumanResources().toBuilder()
                .student(newStudent())
                .student(newStudent())
                .build()
        def humanResources = StudentHumanResourcesAggregateRoot.from(snapshot)

        and: "student's application matching all requirements"
        def application = basicStudentApplication(snapshot)

        when: "student applies for enrollment"
        def result = humanResources.considerApplication(application)

        then: "student should not be enrolled"
        def students = humanResources.createSnapshot().students()
        students.size() == snapshot.students().size()

        and: "Student Enrolled event should not be created"
        result.value().isEmpty()
        result.resultReason() == NO_VACANCY
    }

    def "student may resign from enrollment"() {
        given: "faculty with an enrolled student"
        def student = newStudent()
        def snapshot = newStudentHumanResources().toBuilder()
                .student(student)
                .build()
        def humanResources = StudentHumanResourcesAggregateRoot.from(snapshot)

        when: "student resigns from enrollment"
        def result = humanResources.receiveResignation(student.id())

        then: "student is marked as inactive"
        def students = humanResources.createSnapshot().students()
        students.size() == 1
        students.any { it.enrollmentState() == INACTIVE }

        and: "Student Resigned event should be created"
        result.value().isPresent()
        def event = result.value().get()
        event.facultyId() == snapshot.id()
        event.studentId() == student.id()
        students.any { it.id() == event.studentId() }
    }

    def "student cannot resign from enrollment when not enrolled at the faculty"() {
        given: "faculty with an enrolled student"
        def enrolled = newStudent()
        def snapshot = newStudentHumanResources().toBuilder()
                .student(enrolled)
                .build()
        def humanResources = StudentHumanResourcesAggregateRoot.from(snapshot)

        and: "other student not enrolled at the faculty"
        def otherStudent = newStudent()

        when: "not enrolled student resigns from enrollment"
        def result = humanResources.receiveResignation(otherStudent.id())

        then: "enrollment resignation should fail"
        def students = humanResources.createSnapshot().students()
        !students.any { it.enrollmentState() == INACTIVE }

        and: "Student Resigned event should not be created"
        result.value().isEmpty()
        result.resultReason() == STUDENT_NOT_ENROLLED
    }
}
