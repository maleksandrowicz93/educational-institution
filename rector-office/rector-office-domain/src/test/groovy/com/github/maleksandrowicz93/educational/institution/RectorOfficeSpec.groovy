package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.events.FacultyCreatedEvent
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.facultySetup
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.getFACULTY_NAME
import static com.github.maleksandrowicz93.educational.institution.utils.RectorOfficeUtils.newRectorOffice
import static java.util.stream.Collectors.toSet

class RectorOfficeSpec extends Specification {

    def "faculty may be created"() {
        given: "Educational Institution with no faculty"
        def snapshot = newRectorOffice()
        def rectorOffice = RectorOfficeAggregateRoot.from(snapshot)

        and: "faculty to be created"
        def facultySetup = facultySetup()

        when: "the faculty is created"
        def result = rectorOffice.createFaculty(facultySetup)

        then: "should be created successfully"
        result.value().isPresent()
        def event = result.value().get()
        def currentSnapshot = rectorOffice.createSnapshot()
        def existingFacultiesIds = currentSnapshot.faculties().stream()
                .map { it.id() }
                .collect(toSet())
        existingFacultiesIds.contains(event.facultyId())
        validateEvent(event, facultySetup, currentSnapshot.id())
    }

    private void validateEvent(
            FacultyCreatedEvent event,
            FacultySetup facultySetup,
            RectorOfficeId rectorOfficeId
    ) {
        assert event.facultyId().value() != null
        assert event.facultyName() == facultySetup.name()
        assert event.rectorOfficeId() == rectorOfficeId
        validateMainFieldOfStudy(event, facultySetup)
        validateSecondaryFieldsOfStudy(event, facultySetup)
    }

    private void validateMainFieldOfStudy(FacultyCreatedEvent event, FacultySetup facultySetup) {
        def mainFieldOfStudy = event.mainFieldOfStudy()
        assert mainFieldOfStudy.id().value() != null
        assert mainFieldOfStudy.facultyId() == event.facultyId()
        assert mainFieldOfStudy.name() == facultySetup.mainFieldOfStudyName().value()
    }

    private void validateSecondaryFieldsOfStudy(FacultyCreatedEvent event, FacultySetup facultySetup) {
        event.secondaryFieldsOfStudy().forEach {
            validateSecondaryFieldOfStudy(it, event.facultyId(), facultySetup)
        }
    }

    private void validateSecondaryFieldOfStudy(
            FieldOfStudySnapshot fieldOfStudySnapshot,
            FacultyId facultyId,
            FacultySetup facultySetup
    ) {
        assert fieldOfStudySnapshot.id().value() != null
        assert fieldOfStudySnapshot.facultyId() == facultyId
        assert facultySetup.secondaryFieldsOfStudyNames()
                .any { it.value() == fieldOfStudySnapshot.name() }
    }

    def "faculty should not be created if its name is already taken"() {
        given: "Educational Institution with a faculty"
        def snapshot = newRectorOffice().toBuilder()
                .faculty(FacultySnapshot.builder()
                        .id(new FacultyId(UUID.randomUUID()))
                        .name(FACULTY_NAME)
                        .build())
                .build()
        def rectorOffice = RectorOfficeAggregateRoot.from(snapshot)

        and: "another faculty to be created with the same name"
        def facultySetup = facultySetup()

        when: "the faculty is created"
        def result = rectorOffice.createFaculty(facultySetup)

        then: "should not be created"
        result.value().isEmpty()
        def currentSnapshot = rectorOffice.createSnapshot()
        currentSnapshot.faculties().size() == snapshot.faculties().size()
    }
}
