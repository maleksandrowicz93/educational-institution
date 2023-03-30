package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.common.EventsPublisher
import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState
import com.github.maleksandrowicz93.educational.institution.enums.EnrollmentState
import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentApplication
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingApplication
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.StudentSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.Threshold
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.basicCourseProposition
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.basicFieldsOfStudy
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.basicProfessorApplication
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.basicStudentApplication
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.courseProposition
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.educationalInstitutionSnapshot
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.facultySetup
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.fakePersonalData
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.professorApplication
import static com.github.maleksandrowicz93.educational.institution.EducationalInstitutionUtils.studentApplication
import static java.util.stream.Collectors.toSet

class EducationalInstitutionSpec extends Specification {

    @Shared
    def snapshot = educationalInstitutionSnapshot()
    @Shared
    def educationalInstitution = EducationalInstitution.from(snapshot, eventsPublisher)
    @Shared
    def facultySetup = facultySetup()
    @Shared
    def faculty = educationalInstitution.createFaculty(facultySetup)

    def eventsPublisher = Mock(EventsPublisher)

    def setup() {
        snapshot = educationalInstitutionSnapshot()
        educationalInstitution = EducationalInstitution.from(snapshot, eventsPublisher)
        facultySetup = facultySetup()
        faculty = educationalInstitution.createFaculty(facultySetup)
    }

    def "faculty may be created"() {
        given: "Educational Institution without any faculty"
        educationalInstitution = EducationalInstitution.from(snapshot, eventsPublisher)

        when: "the faculty is created"
        faculty = educationalInstitution.createFaculty(facultySetup)

        then: "should be created successfully"
        def currentSnapshot = educationalInstitution.createSnapshot()
        !currentSnapshot.faculties().isEmpty()
        faculty.id().value() != null
        faculty.name() == facultySetup.name()
        validateMainFieldOfStudy(faculty, facultySetup)
        validateSecondaryFieldsOfStudy(faculty, facultySetup)
    }

    private void validateMainFieldOfStudy(FacultySnapshot facultySnapshot, FacultySetup facultySetup) {
        def mainFieldOfStudy = facultySnapshot.mainFieldOfStudy()
        assert mainFieldOfStudy.id().value() != null
        assert mainFieldOfStudy.facultyId() == facultySnapshot.id()
        assert mainFieldOfStudy.name() == facultySetup.mainFieldOfStudyName().value()
    }

    private void validateSecondaryFieldsOfStudy(FacultySnapshot facultySnapshot, FacultySetup facultySetup) {
        facultySnapshot.secondaryFieldsOfStudy().forEach {
            validateSecondaryFieldOfStudy(it, facultySnapshot.id(), facultySetup)
        }
    }

    private void validateSecondaryFieldOfStudy(
            FieldOfStudySnapshot fieldOfStudySnapshot,
            FacultyId facultyId,
            FacultySetup facultySetup
    ) {
        assert fieldOfStudySnapshot.id().value() != null
        assert fieldOfStudySnapshot.facultyId() == facultyId
        assert facultySetup.secondaryFieldsOfStudyNames().stream()
                .map { it.value() }
                .collect(toSet())
                .contains(fieldOfStudySnapshot.name())
    }

    def "professor matching all requirements may be employed"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(faculty)

        when: "professor applies for hiring"
        def maybeHired = educationalInstitution.considerHiring(application)

        then: "professor should be employed"
        def professors = getProfessors()
        !professors.isEmpty()
        maybeHired.isPresent()
        with(maybeHired.get()) {
            id().value() != null
            personalData() == application.personalData()
            fieldsOfStudy() == application.fieldsOfStudy()
            facultyId() == application.facultyId()
            employmentState() == EmploymentState.EMPLOYED
        }
    }

    private Set<ProfessorSnapshot> getProfessors() {
        def currentSnapshot = educationalInstitution.createSnapshot()
        currentSnapshot.faculties().stream()
                .filter { it.id() == faculty.id() }
                .map { it.professors() }
                .flatMap { it.stream() }
                .collect(toSet())
    }

    @Unroll("years of experience: #yearsOfExperience, fields of study number: #fieldsOfStudy.size()")
    def "professor not matching requirements should not be employed"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "professor's application not matching all requirements"
        def application = professorApplication(faculty.id(), yearsOfExperience, fieldsOfStudy)

        when: "professor applies for hiring"
        def maybeHired = educationalInstitution.considerHiring(application)

        then: "professor should not be employed"
        def professors = getProfessors()
        professors.isEmpty()
        maybeHired.isEmpty()

        where:
        yearsOfExperience | fieldsOfStudy
        1                 | basicFieldsOfStudy(faculty)
        2                 | Set.of(faculty.mainFieldOfStudy().id())
        1                 | Set.of(faculty.mainFieldOfStudy().id())
    }

    def "professor should not be employed when no vacancy at faculty"() {
        given: "Educational Institution with no vacancy at a faculty"
        educationalInstitution.considerHiring(basicProfessorApplication(faculty))
        educationalInstitution.considerHiring(basicProfessorApplication(faculty))

        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(faculty)

        when: "professor applies for hiring"
        def maybeHired = educationalInstitution.considerHiring(application)

        then: "professor should not be employed"
        def professors = getProfessors()
        professors.size() == 2
        maybeHired.isEmpty()
    }

    def "professor may resign from employment"() {
        given: "Educational Institution with a professor hired at a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()

        and: "courses led by professor"
        def courseProposition1 = basicCourseProposition(hired, faculty)
        def courseProposition2 = basicCourseProposition(hired, faculty)
        educationalInstitution.considerCourseCreation(courseProposition1)
        educationalInstitution.considerCourseCreation(courseProposition2)

        when: "professor resigns from employment"
        def resigned = educationalInstitution.receiveHiringResignation(hired.id())

        then: "all courses led by him become vacated"
        def courses = getCourses()
        def allCoursesVacated = courses.stream()
                .filter { it.professorId() == resigned.id() }
                .map { it.state() }
                .allMatch { it == CourseState.FREE }
        allCoursesVacated

        and: "professor is marked as inactive"
        resigned.employmentState() == EmploymentState.INACTIVE

        and: "external system should be notified"
        1 * eventsPublisher.publish(_)
    }

    def "student matching all requirements may be enrolled"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "student's application matching all requirements"
        def application = basicStudentApplication(faculty)

        when: "student applies for enrollment"
        def maybeEnrolled = educationalInstitution.considerEnrollment(application)

        then: "student should be enrolled"
        def students = getStudents()
        !students.isEmpty()
        maybeEnrolled.isPresent()
        with(maybeEnrolled.get()) {
            id().value() != null
            fakePersonalData() == application.personalData()
            facultyId() == faculty.id()
            enrollmentState() == EnrollmentState.ENROLLED
        }
    }

    private Set<StudentSnapshot> getStudents() {
        def currentSnapshot = educationalInstitution.createSnapshot()
        currentSnapshot.faculties().stream()
                .filter { it.id() == faculty.id() }
                .map { it.students() }
                .flatMap { it.stream() }
                .collect(toSet())
    }

    @Unroll("main test result: #mainTestResult, secondary test result: #secondaryTestResult")
    def "student not matching requirements should not be enrolled"() {
        given: "Educational Institution with vacancy at a faculty"
        and: "student's application not matching requirements"
        def application = studentApplication(faculty, mainTestResult, secondaryTestResult)

        when: "student applies for enrollment"
        def maybeEnrolled = educationalInstitution.considerEnrollment(application)

        then: "student should not be enrolled"
        def students = getStudents()
        students.isEmpty()
        maybeEnrolled.isEmpty()

        where:
        mainTestResult | secondaryTestResult
        80             | 0
        0              | 80
        0              | 0
    }

    def "student should not be enrolled when no vacancy at faculty"() {
        given: "Educational Institution with no vacancy at a faculty"
        educationalInstitution.considerEnrollment(basicStudentApplication(faculty))
        educationalInstitution.considerEnrollment(basicStudentApplication(faculty))

        and: "student's application matching all requirements"
        def application = basicStudentApplication(faculty)

        when: "student applies for enrollment"
        def maybeEnrolled = educationalInstitution.considerEnrollment(application)

        then: "student should not be enrolled"
        def students = getStudents()
        students.size() == 2
        maybeEnrolled.isEmpty()
    }

    def "student may resign from enrollment"() {
        given: "Educational Institution with a student enrolled at a faculty"
        def studentApplication = basicStudentApplication(faculty)
        def enrolled = educationalInstitution.considerEnrollment(studentApplication).get()

        when: "student resigns from enrollment"
        def resigned = educationalInstitution.receiveEnrollmentResignation(enrolled.id())

        then: "all courses student is enrolled for become vacated"
        def studentCoursesBeforeResignation = faculty.courses().stream()
                .filter { it.students().contains(resigned.id()) }
                .map { it.id() }
                .collect(toSet())
        def allCoursesAfterResignation = getCourses()
        def studentCoursesVacated = allCoursesAfterResignation.stream()
                .filter { studentCoursesBeforeResignation.contains(it.id()) }
                .noneMatch { it.students().contains(resigned.id()) }
        studentCoursesVacated

        and: "student is marked as inactive"
        resigned.enrollmentState() == EnrollmentState.INACTIVE

        and: "external system should be notified"
        1 * eventsPublisher.publish(_)
    }

    def "professor with capacity may create a course matching all requirements within a faculty"() {
        given: "Educational Institution with a professor with capacity hired at a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()

        and: "course matching all requirements to be created"
        def courseProposition = basicCourseProposition(hired, faculty)

        when: "professor creates course"
        def maybeCreated = educationalInstitution.considerCourseCreation(courseProposition)

        then: "course should be created"
        def courses = getCourses()
        !courses.isEmpty()
        maybeCreated.isPresent()
        with(maybeCreated.get()) {
            id().value() != null
            name() == courseProposition.name()
            facultyId() == courseProposition.facultyId()
            professorId() == courseProposition.professorId()
            fieldsOfStudy() == courseProposition.fieldsOfStudy()
            students().isEmpty()
            state() == CourseState.LED
        }
    }

    private Set<CourseSnapshot> getCourses() {
        def currentSnapshot = educationalInstitution.createSnapshot()
        currentSnapshot.faculties().stream()
                .filter { it.id() == faculty.id() }
                .map { it.courses() }
                .map { it.stream() }
                .collect(toSet())
    }

    @Unroll("fields of study: #fieldsOfStudy")
    def "professor with capacity should not create a course not matching requirements within a faculty"() {
        given: "Educational Institution with a professor with capacity hired at a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()

        and: "course not matching requirements to be created"
        def courseProposition = courseProposition(hired, fieldsOfStudy)

        when: "professor creates course"
        def maybeCreated = educationalInstitution.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def courses = getCourses()
        courses.isEmpty()
        maybeCreated.isEmpty()

        where:
        fieldsOfStudy                                    | _
        tooFewFieldsOfStudy() as Set<FieldOfStudyId>     | _
        tooManyFieldsOfStudy() as Set<FieldOfStudyId>    | _
        notMatchedFieldsOfStudy() as Set<FieldOfStudyId> | _
    }

    def tooFewFieldsOfStudy() {
        def basicFieldOfStudy = basicFieldsOfStudy(faculty).stream()
                .findFirst()
                .get()
        Set.of(basicFieldOfStudy)
    }

    def tooManyFieldsOfStudy() {
        def basicFieldsOfStudyArray = basicFieldsOfStudy(faculty)
                .toArray(new FieldOfStudyId[0]) as FieldOfStudyId[]
        def additionalFieldOfStudy = faculty.mainFieldOfStudy().id()
        Set.of(basicFieldsOfStudyArray, additionalFieldOfStudy)
    }

    def notMatchedFieldsOfStudy() {
        def basicFieldOfStudy = basicFieldsOfStudy(faculty).stream()
                .findFirst()
                .get()
        def additionalFieldOfStudy = faculty.mainFieldOfStudy().id()
        Set.of(basicFieldOfStudy, additionalFieldOfStudy)
    }

    def "professor with capacity should not create a course matching all requirements within a full faculty"() {
        given: "Educational Institution with a 2 professors with capacity hired at a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired1 = educationalInstitution.considerHiring(professorApplication).get()
        def hired2 = educationalInstitution.considerHiring(professorApplication).get()

        and: "enough number of courses created by first professor to match faculty maximum courses threshold"
        educationalInstitution.considerCourseCreation(basicCourseProposition(hired1, faculty))
        educationalInstitution.considerCourseCreation(basicCourseProposition(hired1, faculty))

        and: "another course to be created"
        def courseProposition = basicCourseProposition(hired2, faculty)

        when: "second professor creates course"
        def maybeCreated = educationalInstitution.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def courses = getCourses()
        courses.isEmpty()
        maybeCreated.isEmpty()
    }

    def "professor with no capacity should not create a course matching all requirements within a faculty"() {
        given: "Educational Institution allowing maximum one led course per professor"
        def maximumLedCourses = new Threshold(1)
        snapshot = educationalInstitutionSnapshot(maximumLedCourses)
        educationalInstitution = EducationalInstitution.from(snapshot, eventsPublisher)
        faculty = educationalInstitution.createFaculty(facultySetup)

        and: "professor with no capacity hired at a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()
        educationalInstitution.considerCourseCreation(basicCourseProposition(hired, faculty))

        and: "course matching all requirements to be created"
        def courseProposition = basicCourseProposition(hired, faculty)

        when: "professor creates course"
        def maybeCreated = educationalInstitution.considerCourseCreation(courseProposition)

        then: "course should not be created"
        def courses = getCourses()
        courses.isEmpty()
        maybeCreated.isEmpty()
    }

    def "professor may resign from leading the course"() {
        given: "Educational Institution with a professor leading a course at a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()
        def courseProposition = basicCourseProposition(hired, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()

        when: "professor resigns from leading the course"
        def vacated = educationalInstitution.receiveCourseLeadingResignation(course.id())

        then: "the course becomes vacated"
        vacated.state() == CourseState.FREE

        and: "external system should be notified"
        1 * eventsPublisher.publish(_)
    }

    def "vacated course may be overtaken by a professor matching all requirements"() {
        given: "Educational Institution with a vacated course within a faculty"
        def resigningProfessorApplication = basicProfessorApplication(faculty)
        def resigning = educationalInstitution.considerHiring(resigningProfessorApplication).get()
        def courseProposition = basicCourseProposition(resigning, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()
        def vacated = educationalInstitution.receiveCourseLeadingResignation(course.id())

        and: "new employed professor matching all course requirements"
        def overtakingProfessorApplication = basicProfessorApplication(faculty)
        def overtaking = educationalInstitution.considerHiring(overtakingProfessorApplication).get()

        and: "professor's application to overtake the course"
        def application = new CourseOvertakingApplication(overtaking.id(), vacated.id())

        when: "this professor overtakes the course"
        def maybeOvertaken = educationalInstitution.considerCourseOvertaking(application)

        then: "course should be overtaken"
        maybeOvertaken.isPresent()
        with(maybeOvertaken.get()) {
            id().value() != null
            name() == vacated.name()
            facultyId() == vacated.facultyId()
            professorId() == overtaking.id()
            fieldsOfStudy() == vacated.fieldsOfStudy()
            students() == vacated.students()
            state() == CourseState.LED
        }
    }

    def "vacated course should not be overtaken by a professor not matching requirements"() {
        given: "Educational Institution with the vacated course within a faculty"
        def resigningProfessorApplication = basicProfessorApplication(faculty)
        def resigning = educationalInstitution.considerHiring(resigningProfessorApplication).get()
        def courseProposition = basicCourseProposition(resigning, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()
        def vacated = educationalInstitution.receiveCourseLeadingResignation(course.id())

        and: "new employed professor not matching course requirements"
        def overtakingProfessorApplication = professorApplication(faculty.id(),
                2, notMatchedFieldsOfStudy())
        def overtaking = educationalInstitution.considerHiring(overtakingProfessorApplication).get()

        and: "this professor's application to overtake the course"
        def application = new CourseOvertakingApplication(overtaking.id(), vacated.id())

        when: "this professor overtakes the course"
        def maybeOvertaken = educationalInstitution.considerCourseOvertaking(application)

        then: "course should not be overtaken"
        maybeOvertaken.isEmpty()
        def ledCourses = getCourses().stream()
                .filter { it.state() == CourseState.LED }
                .collect() { toSet() }
        ledCourses.isEmpty()
    }

    def "vacated course should not be overtaken by a professor from other faculty"() {
        given: "Educational Institution with 2 faculties"
        def alternativeFaculty = educationalInstitution.createFaculty(facultySetup)

        and: "vacated course within first faculty"
        def resigningProfessorApplication = basicProfessorApplication(faculty)
        def resigning = educationalInstitution.considerHiring(resigningProfessorApplication).get()
        def courseProposition = basicCourseProposition(resigning, faculty)
        def created = educationalInstitution.considerCourseCreation(courseProposition).get()
        def vacated = educationalInstitution.receiveCourseLeadingResignation(created.id())

        and: "professor matching all requirements employed in second faculty"
        def overtakingProfessorApplication = basicProfessorApplication(alternativeFaculty)
        def overtaking = educationalInstitution.considerHiring(overtakingProfessorApplication).get()

        and: "professor's application to overtake the course"
        def overtakingApplication = new CourseOvertakingApplication(overtaking.id(), vacated.id())

        when: "professor overtakes the curse"
        def maybeOvertaken = educationalInstitution.considerCourseOvertaking(overtakingApplication)

        then: "course should not be overtaken"
        maybeOvertaken.isEmpty()
        def ledCourses = getCourses().stream()
                .filter { it.state() == CourseState.LED }
                .collect() { toSet() }
        ledCourses.isEmpty()
    }

    def "student may enroll for the vacated course"() {
        given: "Educational Institution with the vacated course within a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()
        def courseProposition = basicCourseProposition(hired, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()

        and: "student enrolled in the faculty"
        def studentApplication = basicStudentApplication(faculty)
        def enrolled = educationalInstitution.considerEnrollment(studentApplication).get()

        and: "student's application for course enrollment"
        def application = new CourseEnrollmentApplication(enrolled.id(), course.id())

        when: "student enrolls for the course"
        def maybeCurrentCourse = educationalInstitution.considerCourseEnrollment(application)

        then: "student should be enrolled"
        def studentCourses = getCourses().stream()
                .filter { it.students().contains(enrolled.id()) }
                .collect(toSet())
        !studentCourses.isEmpty()
        maybeCurrentCourse.isPresent()
        with(maybeCurrentCourse.get()) {
            id() == course.id()
            name() == course.name()
            facultyId() == course.facultyId()
            professorId() == course.professorId()
            fieldsOfStudy() == course.fieldsOfStudy()
            !students().isEmpty()
            students().contains(enrolled.id())
            state() == course.state()
        }
    }

    def "student should not be enrolled for the full course"() {
        given: "Educational Institution with a course within a faculty"
        def hired = educationalInstitution.considerHiring(basicProfessorApplication(faculty)).get()
        def courseProposition = basicCourseProposition(hired, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()

        and: "the course has no vacancy"
        def studentApplication1 = basicStudentApplication(faculty)
        def studentApplication2 = basicStudentApplication(faculty)
        def student1 = educationalInstitution.considerEnrollment(studentApplication1).get()
        def student2 = educationalInstitution.considerEnrollment(studentApplication2).get()
        educationalInstitution.considerCourseEnrollment(new CourseEnrollmentApplication(student1.id(), course.id()))
        educationalInstitution.considerCourseEnrollment(new CourseEnrollmentApplication(student2.id(), course.id()))

        and: "new student enrolled in the faculty"
        def newStudentApplication = basicStudentApplication(faculty)
        def newStudent = educationalInstitution.considerEnrollment(newStudentApplication).get()

        and: "new student's course enrollment application"
        def application = new CourseEnrollmentApplication(newStudent.id(), course.id())

        when: "student enrolls for the course"
        def maybeCurrentCourse = educationalInstitution.considerCourseEnrollment(application)

        then: "student should not be enrolled"
        maybeCurrentCourse.isEmpty()
        def studentsEnrolledForCourse = getCourses().stream()
                .filter { it.id() == course.id() }
                .map { it.students() }
                .flatMap { it.stream() }
                .collect(toSet())
        !studentsEnrolledForCourse.isEmpty()
        !studentsEnrolledForCourse.contains(newStudent.id())
    }

    def "student should not be enrolled for the vacated course from other faculty"() {
        given: "Educational Institution with 2 faculties"
        def alternativeFaculty = educationalInstitution.createFaculty(facultySetup)

        and: "vacated course within first faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()
        def courseProposition = basicCourseProposition(hired, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()

        and: "student enrolled in second faculty"
        def studentApplication = basicStudentApplication(alternativeFaculty)
        def student = educationalInstitution.considerEnrollment(studentApplication).get()

        and: "student's application for the course enrollment"
        def application = new CourseEnrollmentApplication(student.id(), course.id())

        when: "student enrolls for the course"
        def maybeCurrentCourse = educationalInstitution.considerCourseEnrollment(application)

        then: "student should not be enrolled"
        maybeCurrentCourse.isEmpty()
        def courses = getCourses()
        def studentsEnrolledForCourse = courses.stream()
                .filter { it.id() == course.id() }
                .map { it.students() }
                .flatMap { it.stream() }
                .collect(toSet())
        studentsEnrolledForCourse.isEmpty()
    }

    def "course with too many vacancies may be closed"() {
        given: "Educational Institution with a professor hired at a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()

        and: "too many vacancies within the course"
        def courseProposition = basicCourseProposition(hired, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()

        when: "professor closes the course"
        def maybeClosed = educationalInstitution.considerClosingCourse(course.id())

        then: "the course should be closed"
        def isCourseClosed = getCourses().stream()
                .filter { it.id() == course.id() }
                .map { it.state() }
                .allMatch { it == CourseState.CLOSED }
        isCourseClosed
        maybeClosed.isPresent()
        with(maybeClosed.get()) {
            id() == course.id()
            name() == course.name()
            facultyId() == course.facultyId()
            professorId() == course.professorId()
            fieldsOfStudy() == course.fieldsOfStudy()
            students().isEmpty()
            state() == CourseState.CLOSED
        }

        and: "external system should be notified"
        1 * eventsPublisher.publish(_)
    }

    def "course with enough number of enrolled students should not be closed"() {
        given: "Educational Institution with the course within a faculty"
        def professorApplication = basicProfessorApplication(faculty)
        def hired = educationalInstitution.considerHiring(professorApplication).get()
        def courseProposition = basicCourseProposition(hired, faculty)
        def course = educationalInstitution.considerCourseCreation(courseProposition).get()

        and: "enough number of students are enrolled for the course"
        def studentApplication1 = basicStudentApplication(faculty)
        def studentApplication2 = basicStudentApplication(faculty)
        def student1 = educationalInstitution.considerEnrollment(studentApplication1).get()
        def student2 = educationalInstitution.considerEnrollment(studentApplication2).get()
        def courseEnrollmentApplication1 = new CourseEnrollmentApplication(student1.id(), course.id())
        def courseEnrollmentApplication2 = new CourseEnrollmentApplication(student2.id(), course.id())
        educationalInstitution.considerCourseEnrollment(courseEnrollmentApplication1).get()
        educationalInstitution.considerCourseEnrollment(courseEnrollmentApplication2).get()

        when: "professor closes the course"
        def maybeClosed = educationalInstitution.considerClosingCourse(course.id())

        then: "the course should not be closed"
        maybeClosed.isEmpty()
        def closedCourses = getCourses().stream()
                .filter { it.state() == CourseState.CLOSED }
                .collect(toSet())
        closedCourses.isEmpty()
    }
}
