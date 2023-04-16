package com.github.maleksandrowicz93.educational.institution


import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.EducationalInstitutionUtils.newEducationalInstitution
import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.facultySetup

class FacultyCreatorSpec extends Specification {

    def "faculty may be created"() {
        given: "Educational Institution without any faculty"
        def snapshot = newEducationalInstitution()
        def facultyCreator = FacultyCreatorAggregateRoot.from(snapshot)

        and: "faculty to be created"
        def facultySetup = facultySetup(snapshot.id())

        when: "the faculty is created"
        def faculty = facultyCreator.createFaculty(facultySetup)

        then: "should be created successfully"
        def currentSnapshot = facultyCreator.createSnapshot()
        currentSnapshot.faculties().contains(faculty.id())
        validateFaculty(faculty, facultySetup, currentSnapshot.id())
    }

    private void validateFaculty(
            FacultySnapshot faculty,
            FacultySetup facultySetup,
            EducationalInstitutionId educationalInstitutionId
    ) {
        assert faculty.id().value() != null
        assert faculty.name() == facultySetup.name()
        assert faculty.educationalInstitutionId() == educationalInstitutionId
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
        assert facultySetup.secondaryFieldsOfStudyNames()
                .any { it.value() == fieldOfStudySnapshot.name() }
    }
}
