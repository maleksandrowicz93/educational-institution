package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResultReason
import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResultReason
import com.github.maleksandrowicz93.educational.institution.results.HiringResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.basicProfessorApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.basicStudentApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newStudent
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourses
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentEnrolledForCourses
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.basicCourseProposition
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.courseProposition
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.newFaculty
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.MAIN_FIELD_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.SECONDARY_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.fieldsOfStudyFromNames
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.notMatchedFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.tooFewFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.tooManyFieldsOfStudy
import static java.util.stream.Collectors.toSet

class FacultySpec extends Specification {

    def "professor matching all requirements may be employed"() {
        given: "faculty with a vacancy"
        def snapshot = newFaculty()
        def faculty = Faculty.from(snapshot)

        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(snapshot.id())

        when: "professor applies for hiring"
        def hiringResult = faculty.considerHiring(application)

        then: "professor should be employed"
        def professors = faculty.createSnapshot().professors()
        def maybeHired = hiringResult.value()
        maybeHired.isPresent()
        with(maybeHired.get()) {
            professors.contains(it)
            id().value() != null
            personalData() == application.personalData()
            facultyId() == application.facultyId()
            fieldsOfStudy() == application.fieldsOfStudy()
            ledCourses().isEmpty()
            employmentState() == EmploymentState.EMPLOYED
        }
    }

    @Unroll("years of experience: #yearsOfExperience, fields of study: #fieldsOfStudyNames, failure reason: #resultReason.text()")
    def "professor not matching requirements should not be employed"() {
        given: "faculty with a vacancy"
        def snapshot = newFaculty()
        def faculty = Faculty.from(snapshot)

        and: "professor's application with too little experience or too little fields of study number"
        def fieldsOfStudy = fieldsOfStudyFromNames(fieldsOfStudyNames, snapshot.id())
        def application = professorApplication(snapshot.id(), yearsOfExperience, fieldsOfStudy)

        when: "professor applies for hiring"
        def hiringResult = faculty.considerHiring(application)

        then: "professor should not be employed"
        def professors = faculty.createSnapshot().professors()
        professors.isEmpty()
        hiringResult.value().isEmpty()
        hiringResult.resultReason() == resultReason

        where:
        yearsOfExperience | fieldsOfStudyNames          | resultReason
        1                 | SECONDARY_FIELDS_OF_STUDY   | HiringResultReason.TOO_LITTLE_EXPERIENCE
        2                 | Set.of(MAIN_FIELD_OF_STUDY) | HiringResultReason.FIELDS_OF_STUDY_NOT_MATCHED
        1                 | Set.of(MAIN_FIELD_OF_STUDY) | HiringResultReason.TOO_LITTLE_EXPERIENCE
    }

    def "professor should not be employed when no vacancy at faculty"() {
        given: "faculty with no vacancy"
        def newFaculty = newFaculty()
        def snapshot = newFaculty.toBuilder()
                .professor(newProfessor(newFaculty.id()))
                .professor(newProfessor(newFaculty.id()))
                .build()
        def faculty = Faculty.from(snapshot)

        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(snapshot.id())

        when: "professor applies for hiring"
        def hiringResult = faculty.considerHiring(application)

        then: "professor should not be employed"
        def professors = faculty.createSnapshot().professors()
        professors.isEmpty()
        hiringResult.value().isEmpty()
        hiringResult.resultReason() == HiringResultReason.NO_VACANCY
    }

    def "professor may resign from employment"() {
        given: "faculty with hired professor leading 2 courses"
        def newFaculty = newFaculty()
        def courses = Set.of(new CourseId(UUID.randomUUID()), new CourseId(UUID.randomUUID()))
        def hired = professorLeadingCourses(newFaculty.id(), courses)
        def snapshot = newFaculty.toBuilder()
                .professor(hired)
                .courses(courses)
                .build()
        def faculty = Faculty.from(snapshot)

        when: "professor resigns from employment"
        def employmentResignationResult = faculty.receiveHiringResignation(hired.id())

        then: "employment resignation should be received correctly"
        def maybeResigned = employmentResignationResult.value()
        maybeResigned.get()

        and: "all courses led by him become vacated"
        def currentSnapshot = faculty.createSnapshot()
        def ledCourses = currentSnapshot.professors().stream()
                .map { it.ledCourses() }
                .flatMap { it.stream() }
                .collect(toSet())
        ledCourses.isEmpty()

        and: "professor is marked as inactive"
        maybeResigned.get().employmentState() == EmploymentState.INACTIVE
    }

    def "professor cannot resign from employment at other faculty"() {
        given: "faculty with hired professor leading 2 courses"
        def newFaculty = newFaculty()
        def courses = Set.of(new CourseId(UUID.randomUUID()), new CourseId(UUID.randomUUID()))
        def hired = professorLeadingCourses(newFaculty.id(), courses)
        def snapshot = newFaculty.toBuilder()
                .professor(hired)
                .courses(courses)
                .build()
        def faculty = Faculty.from(snapshot)

        and: "professor employed at other faculty"
        def otherProfessor = newProfessor(new FacultyId(UUID.randomUUID()))

        when: "professor resigns from employment foreign faculty"
        def employmentResignationResult = faculty.receiveHiringResignation(otherProfessor.id())

        then: "employment resignation should fail"
        employmentResignationResult.value().isEmpty()
        employmentResignationResult.resultReason() == EmploymentResignationResultReason.INCORRECT_STUDENT_ID

        and: "faculty should stay the same"
        faculty.createSnapshot() == snapshot
    }

    def "student matching all requirements may be enrolled"() {
        given: "faculty with a vacancy"
        def snapshot = newFaculty()
        def faculty = Faculty.from(snapshot)

        and: "student's application matching all requirements"
        def application = basicStudentApplication(snapshot)

        when: "student applies for enrollment"
        def enrollmentResult = faculty.considerEnrollment(application)

        then: "student should be enrolled"
        def students = faculty.createSnapshot().students()
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
        def faculty = Faculty.from(snapshot)

        and: "student's application not matching requirements"
        def application = studentApplication(snapshot, mainTestResult, secondaryTestResult)

        when: "student applies for enrollment"
        def enrollmentResult = faculty.considerEnrollment(application)

        then: "student should not be enrolled"
        def students = faculty.createSnapshot().students()
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
        def faculty = Faculty.from(snapshot)

        and: "student's application matching all requirements"
        def application = basicStudentApplication(snapshot)

        when: "student applies for enrollment"
        def enrollmentResult = faculty.considerEnrollment(application)

        then: "student should not be enrolled"
        def students = faculty.createSnapshot().students()
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
        def faculty = Faculty.from(snapshot)

        when: "student resigns from enrollment"
        def enrollmentResignationResult = faculty.receiveEnrollmentResignation(student.id())

        then: "enrollment resignation should be received correctly"
        def maybeResigned = enrollmentResignationResult.value()
        maybeResigned.isPresent()

        and: "all courses student is enrolled for become vacated"
        def currentSnapshot = faculty.createSnapshot()
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
        def faculty = Faculty.from(snapshot)

        and: "student enrolled at other faculty"
        def otherStudent = newStudent(new FacultyId(UUID.randomUUID()))

        when: "student resigns from enrollment"
        def enrollmentResignationResult = faculty.receiveEnrollmentResignation(otherStudent.id())

        then: "enrollment resignation should fail"
        enrollmentResignationResult.value().isEmpty()
        enrollmentResignationResult.resultReason() == EnrollmentResignationResultReason.INCORRECT_STUDENT_ID

        and: "faculty should stay the same"
        faculty.createSnapshot() == snapshot
    }

    def "professor with capacity may create a course matching all requirements within a faculty"() {
        given: "faculty with hired professor with capacity"
        def newFaculty = newFaculty()
        def hired = newProfessor(newFaculty.id())
        def snapshot = newFaculty.toBuilder()
                .professor(hired)
                .build()
        def faculty = Faculty.from(snapshot)

        and: "course matching all requirements to be created"
        def courseProposition = basicCourseProposition(hired, snapshot.id())

        when: "professor creates course"
        def courseCreationResult = faculty.considerCourseCreation(courseProposition)

        then: "course should be created"
        def courses = faculty.createSnapshot().courses()
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
        def faculty = Faculty.from(snapshot)

        and: "course not matching requirements to be created"
        def fieldsOfStudy = fieldsOfStudyFromNames(fieldsOfStudyNames, snapshot.id())
        def courseProposition = courseProposition(hired, fieldsOfStudy)

        when: "professor creates course"
        def courseCreationResult = faculty.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def courses = faculty.createSnapshot().courses()
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
        def faculty = Faculty.from(snapshot)

        and: "new course to be created"
        def courseProposition = basicCourseProposition(freeProfessor, snapshot.id())

        when: "free professor creates the course"
        def courseCreationResult = faculty.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def currentCourses = faculty.createSnapshot().courses()
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
        def faculty = Faculty.from(snapshot)

        and: "course matching all requirements to be created"
        def newCourseProposition = basicCourseProposition(hired, snapshot.id())

        when: "professor creates course"
        def courseCreationResult = faculty.considerCourseCreation(newCourseProposition)

        then: "course should not be created"
        def currentCourses = faculty.createSnapshot().courses()
        currentCourses == courses
        courseCreationResult.value().isEmpty()
        courseCreationResult.resultReason() == CourseCreationResultReason.NO_PROFESSOR_CAPACITY
    }
}
