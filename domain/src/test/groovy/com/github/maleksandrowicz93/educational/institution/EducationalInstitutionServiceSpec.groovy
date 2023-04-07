package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.api.business.EducationalInstitutionDomainService
import com.github.maleksandrowicz93.educational.institution.common.EventsPublisher
import com.github.maleksandrowicz93.educational.institution.common.SimpleEventsPublisher
import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.events.CourseClosingEvent
import com.github.maleksandrowicz93.educational.institution.events.LeadingCourseResignationEvent
import com.github.maleksandrowicz93.educational.institution.events.ProfessorHiringResignationEvent
import com.github.maleksandrowicz93.educational.institution.events.StudentEnrollmentResignationEvent
import com.github.maleksandrowicz93.educational.institution.reposiotry.CourseSimpleRepository
import com.github.maleksandrowicz93.educational.institution.reposiotry.EducationalInstitutionSimpleRepository
import com.github.maleksandrowicz93.educational.institution.reposiotry.FacultySimpleRepository
import com.github.maleksandrowicz93.educational.institution.repository.CourseRepository
import com.github.maleksandrowicz93.educational.institution.repository.EducationalInstitutionRepository
import com.github.maleksandrowicz93.educational.institution.repository.FacultyRepository
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResultReason
import com.github.maleksandrowicz93.educational.institution.results.CourseCreationResultReason
import com.github.maleksandrowicz93.educational.institution.results.CourseLeadingResignationResultReason
import com.github.maleksandrowicz93.educational.institution.results.EmploymentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.results.EnrollmentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.results.FacultyCreationResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newStudent
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.studentEnrolledForCourse
import static com.github.maleksandrowicz93.educational.institution.utils.CourseUtils.newCourse
import static com.github.maleksandrowicz93.educational.institution.utils.EducationalInstitutionUtils.newEducationalInstitution
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.basicCourseProposition
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.facultySetup
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.newFaculty

class EducationalInstitutionServiceSpec extends Specification {

    EducationalInstitutionRepository educationalInstitutionRepository
    FacultyRepository facultyRepository
    CourseRepository courseRepository
    EventsPublisher eventsPublisher
    EducationalInstitutionDomainService service

    def setup() {
        educationalInstitutionRepository = new EducationalInstitutionSimpleRepository()
        facultyRepository = new FacultySimpleRepository()
        courseRepository = new CourseSimpleRepository()
        eventsPublisher = new SimpleEventsPublisher()
        service = new EducationalInstitutionService(educationalInstitutionRepository, facultyRepository,
                courseRepository, eventsPublisher)
    }

    def "created faculty should be stored in proper repositories"() {
        given: "educational institution"
        def educationalInstitution = newEducationalInstitution()
        educationalInstitutionRepository.save(educationalInstitution)

        and: "faculty setup to be created"
        def facultySetup = facultySetup(educationalInstitution.id())

        when: "faculty is created"
        def facultyCreationResult = service.createFaculty(facultySetup)

        then: "should be created successfully"
        def maybeCreated = facultyCreationResult.value()
        maybeCreated.isPresent()
        def created = maybeCreated.get()

        and: "should be stored both in educational institution and faulty repositories"
        def maybeFaculties = educationalInstitutionRepository.findById(educationalInstitution.id())
                .map { it.faculties() }
        maybeFaculties.isPresent()
        maybeFaculties.get().contains(created.id())
        facultyRepository.existsById(created.id())
    }

    def "nothing should be stored in repository at faculty creation failure"() {
        given: "faculty setup to be created at not existing educational institution"
        def notKnownId = new EducationalInstitutionId(UUID.randomUUID())
        def facultySetup = facultySetup(notKnownId)

        when: "faculty is created"
        def facultyCreationResult = service.createFaculty(facultySetup)

        then: "should not be created successfully"
        facultyCreationResult.value().isEmpty()
        facultyCreationResult.resultReason() == FacultyCreationResultReason.INCORRECT_EDUCATIONAL_INSTITUTION_ID

        and: "should not be stored in any repository"
        !educationalInstitutionRepository.existsById(notKnownId)
    }

    def "created course should be stored in proper repositories"() {
        given: "faculty with hired professor"
        def newFaculty = newFaculty()
        def hired = newProfessor(newFaculty.id())
        def faculty = newFaculty.toBuilder()
                .professor(hired)
                .build()
        facultyRepository.save(faculty)

        and: "course proposition to be created"
        def courseProposition = basicCourseProposition(hired, faculty.id())

        when: "course is created"
        def courseCreationResult = service.createCourse(courseProposition)

        then: "should be created successfully"
        def maybeCreated = courseCreationResult.value()
        maybeCreated.isPresent()
        def created = maybeCreated.get()

        and: "should be stored both in faulty and course repositories"
        def maybeCourses = facultyRepository.findById(faculty.id())
                .map { it.courses() }
        maybeCourses.isPresent()
        maybeCourses.get().contains(created.id())
        courseRepository.existsById(created.id())
    }

    def "nothing should be stored in repository at course creation failure"() {
        given: "course proposition to be created at not existing faculty"
        def notKnownFacultyId = new FacultyId(UUID.randomUUID())
        def notKnownProfessor = newProfessor(notKnownFacultyId)
        def courseProposition = basicCourseProposition(notKnownProfessor, notKnownFacultyId)

        when: "course is created"
        def facultyCreationResult = service.createCourse(courseProposition)

        then: "should not be created successfully"
        facultyCreationResult.value().isEmpty()
        facultyCreationResult.resultReason() == CourseCreationResultReason.INCORRECT_FACULTY_ID

        and: "should not be stored in any repository"
        !facultyRepository.existsById(notKnownFacultyId)
    }

    def "after successful employment resignation notification should be sent"() {
        given: "faculty with hired professor"
        def newFaculty = newFaculty()
        def hired = newProfessor(newFaculty.id())
        def snapshot = newFaculty.toBuilder()
                .professor(hired)
                .build()
        facultyRepository.save(snapshot)

        when: "professor resigns from employment"
        def employmentResignationResult = service.resignFromEmployment(hired)

        then: "resignation should be received successfully"
        employmentResignationResult.value().isPresent()

        and: "faculty should be updated in repository"
        def maybeFaculty = facultyRepository.findById(snapshot.id())
        maybeFaculty.isPresent()
        def faculty = maybeFaculty.get()
        def maybeResigned = faculty.professors().stream()
                .filter { it.id() == hired.id() }
                .findFirst()
        maybeResigned.isPresent()
        def resigned = maybeResigned.get()

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new ProfessorHiringResignationEvent(resigned))
    }

    def "after unsuccessful employment resignation notification should not be sent"() {
        given: "professor hired at not known faculty"
        def notKnownFacultyId = new FacultyId(UUID.randomUUID())
        def notKnownProfessor = newProfessor(notKnownFacultyId)

        when: "professor resigns from employment"
        def employmentResignationResult = service.resignFromEmployment(notKnownProfessor)

        then: "resignation should not be received successfully"
        employmentResignationResult.value().isEmpty()
        employmentResignationResult.resultReason() == EmploymentResignationResultReason.INCORRECT_FACULTY_ID

        and: "should not be stored in any repository"
        !facultyRepository.existsById(notKnownFacultyId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }

    def "after successful enrollment resignation notification should be sent"() {
        given: "faculty with enrolled student"
        def newFaculty = newFaculty()
        def enrolled = newStudent(newFaculty.id())
        def faculty = newFaculty.toBuilder()
                .student(enrolled)
                .build()
        facultyRepository.save(faculty)

        when: "student resigns from enrollment"
        def enrollmentResignationResult = service.resignFromEnrollment(enrolled)

        then: "resignation should be received successfully"
        enrollmentResignationResult.value().isPresent()

        and: "faculty should be updated in repository"
        def maybeFaculty = facultyRepository.findById(faculty.id())
        maybeFaculty.isPresent()
        def course = maybeFaculty.get()
        def maybeResigned = course.students().stream()
                .filter { it.id() == enrolled.id() }
                .findFirst()
        maybeResigned.isPresent()
        def resigned = maybeResigned.get()

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new StudentEnrollmentResignationEvent(resigned))
    }

    def "after unsuccessful enrollment resignation notification should not be sent"() {
        given: "student enrolled at not known faculty"
        def notKnownFacultyId = new FacultyId(UUID.randomUUID())
        def notKnownStudent = newStudent(notKnownFacultyId)

        when: "student resigns from enrollment"
        def enrollmentResignationResult = service.resignFromEnrollment(notKnownStudent)

        then: "resignation should not be received successfully"
        enrollmentResignationResult.value().isEmpty()
        enrollmentResignationResult.resultReason() == EnrollmentResignationResultReason.INCORRECT_FACULTY_ID

        and: "should not be stored in any repository"
        !facultyRepository.existsById(notKnownFacultyId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }

    def "after successful course leading resignation notification should be sent"() {
        given: "course led by professor"
        def newCourse = newCourse()
        def professor = newProfessor(newCourse.facultyId())
        def course = newCourse.toBuilder()
                .professor(professor)
                .build()
        courseRepository.save(course)

        when: "professor resigns from leading course"
        def employmentResignationResult = service.resignFromLeadingCourse(course.id())

        then: "resignation should be received successfully"
        employmentResignationResult.value().isPresent()

        and: "course should be updated in repository"
        def maybeNotLedCourse = courseRepository.findById(course.id())
        maybeNotLedCourse.isPresent()
        def notLedCourse = maybeNotLedCourse.get()
        notLedCourse.professor() == null

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new LeadingCourseResignationEvent(notLedCourse))
    }

    def "after unsuccessful course leading resignation notification should not be sent"() {
        given: "course led by professor"
        def freeCourse = newCourse()
        def professor = newProfessor(freeCourse.facultyId())
        def ledCourse = freeCourse.toBuilder()
                .professor(professor)
                .build()
        courseRepository.save(ledCourse)

        and: "not known course"
        def notKnownId = new CourseId(UUID.randomUUID())

        when: "professor resigns from leading other course"
        def courseLeadingResignationResult = service.resignFromLeadingCourse(notKnownId)

        then: "resignation should not be received successfully"
        courseLeadingResignationResult.value().isEmpty()
        courseLeadingResignationResult.resultReason() == CourseLeadingResignationResultReason.INCORRECT_COURSE_ID

        and: "should not be stored in any repository"
        !courseRepository.existsById(notKnownId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }

    def "after successful course closing notification should be sent"() {
        given: "course led by professor with enrolled students"
        def newCourse = newCourse()
        def professor = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def student1 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def student2 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(professor)
                .student(student1)
                .student(student2)
                .build()
        courseRepository.save(snapshot)

        when: "professor closes the course"
        def courseClosingResult = service.closeCourse(snapshot.id())

        then: "course should be closed successfully"
        courseClosingResult.value().isPresent()

        and: "course should be updated in repository"
        def maybeClosed = courseRepository.findById(snapshot.id())
        maybeClosed.isPresent()
        def closed = maybeClosed.get()
        closed.professor() == null
        closed.state() == CourseState.CLOSED

        and: "a specific notification should be sent"
        eventsPublisher.events().contains(new CourseClosingEvent(closed))
    }

    def "after unsuccessful course closing notification should not be sent"() {
        given: "course led by professor with enrolled students"
        def newCourse = newCourse()
        def professor = professorLeadingCourse(newCourse.facultyId(), newCourse.id())
        def student1 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def student2 = studentEnrolledForCourse(newCourse.facultyId(), newCourse.id())
        def snapshot = newCourse.toBuilder()
                .professor(professor)
                .student(student1)
                .student(student2)
                .build()
        courseRepository.save(snapshot)

        and: "not known other course"
        def notKnownId = new CourseId(UUID.randomUUID())

        when: "professors closes the other course"
        def courseClosingResult = service.closeCourse(notKnownId)

        then: "course should not be closed successfully"
        courseClosingResult.value().isEmpty()
        courseClosingResult.resultReason() == CourseClosingResultReason.INCORRECT_COURSE_ID

        and: "should not be stored in any repository"
        !courseRepository.existsById(notKnownId)

        and: "no notification should be sent"
        eventsPublisher.events().isEmpty()
    }
}
