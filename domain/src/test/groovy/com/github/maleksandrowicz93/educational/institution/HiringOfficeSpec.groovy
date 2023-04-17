package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState
import com.github.maleksandrowicz93.educational.institution.result.reasons.EmploymentResignationResultReason
import com.github.maleksandrowicz93.educational.institution.result.reasons.HiringResultReason
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.basicProfessorApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.newProfessor
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorApplication
import static com.github.maleksandrowicz93.educational.institution.utils.CandidatesUtils.professorLeadingCourses
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.newFaculty
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.fieldsOfStudyFromNames
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.getMAIN_FIELD_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.getSECONDARY_FIELDS_OF_STUDY
import static java.util.stream.Collectors.toSet

class HiringOfficeSpec extends Specification {

    def "professor matching all requirements may be employed"() {
        given: "faculty with a vacancy"
        def snapshot = newFaculty()
        def hiringOffice = HiringOfficeAggregateRoot.from(snapshot)

        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(snapshot.id())

        when: "professor applies for hiring"
        def hiringResult = hiringOffice.considerHiring(application)

        then: "professor should be employed"
        def professors = hiringOffice.createSnapshot().professors()
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
        def hiringOffice = HiringOfficeAggregateRoot.from(snapshot)

        and: "professor's application with too little experience or too little fields of study number"
        def fieldsOfStudy = fieldsOfStudyFromNames(fieldsOfStudyNames, snapshot.id())
        def application = professorApplication(snapshot.id(), yearsOfExperience, fieldsOfStudy)

        when: "professor applies for hiring"
        def hiringResult = hiringOffice.considerHiring(application)

        then: "professor should not be employed"
        def professors = hiringOffice.createSnapshot().professors()
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
        def hiringOffice = HiringOfficeAggregateRoot.from(snapshot)

        and: "professor's application matching all requirements"
        def application = basicProfessorApplication(snapshot.id())

        when: "professor applies for hiring"
        def hiringResult = hiringOffice.considerHiring(application)

        then: "professor should not be employed"
        def professors = hiringOffice.createSnapshot().professors()
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
        def hiringOffice = HiringOfficeAggregateRoot.from(snapshot)

        when: "professor resigns from employment"
        def employmentResignationResult = hiringOffice.receiveHiringResignation(hired.id())

        then: "employment resignation should be received correctly"
        def maybeResigned = employmentResignationResult.value()
        maybeResigned.get()

        and: "all courses led by him become vacated"
        def currentSnapshot = hiringOffice.createSnapshot()
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
        def hiringOffice = HiringOfficeAggregateRoot.from(snapshot)

        and: "professor employed at other faculty"
        def otherProfessor = newProfessor(new FacultyId(UUID.randomUUID()))

        when: "professor resigns from employment foreign faculty"
        def employmentResignationResult = hiringOffice.receiveHiringResignation(otherProfessor.id())

        then: "employment resignation should fail"
        employmentResignationResult.value().isEmpty()
        employmentResignationResult.resultReason() == EmploymentResignationResultReason.INCORRECT_PROFESSOR_ID

        and: "faculty should stay the same"
        hiringOffice.createSnapshot() == snapshot
    }
}
