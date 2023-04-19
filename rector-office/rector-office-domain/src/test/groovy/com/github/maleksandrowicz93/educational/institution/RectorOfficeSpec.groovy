package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.FACULTY_NAME
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.facultySetup
import static com.github.maleksandrowicz93.educational.institution.utils.RectorOfficeUtils.newRectorOffice

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
        event.rectorOfficeId() == currentSnapshot.id()
        event.facultyManagementThresholds() == snapshot.thresholds()
        validateFaculty(event.facultySnapshot(), currentSnapshot, facultySetup)
    }

    private void validateFaculty(
            FacultySnapshot facultySnapshot,
            RectorOfficeSnapshot rectorOfficeSnapshot,
            FacultySetup facultySetup
    ) {
        assert facultySnapshot.id().value() != null
        assert rectorOfficeSnapshot.faculties().any { it.id() == facultySnapshot.id() }
        assert facultySnapshot.name() == facultySetup.name()
        validateMainFieldOfStudy(facultySnapshot.mainFieldOfStudy(), facultySetup)
        facultySnapshot.secondaryFieldsOfStudy().forEach {
            validateSecondaryFieldOfStudy(it, facultySetup)
        }
    }

    private void validateMainFieldOfStudy(FieldOfStudySnapshot mainFieldOfStudy, FacultySetup facultySetup) {
        assert mainFieldOfStudy.id().value() != null
        assert mainFieldOfStudy.name() == facultySetup.mainFieldOfStudyName().value()
    }

    private void validateSecondaryFieldOfStudy(FieldOfStudySnapshot fieldOfStudySnapshot, FacultySetup facultySetup) {
        assert fieldOfStudySnapshot.id().value() != null
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
