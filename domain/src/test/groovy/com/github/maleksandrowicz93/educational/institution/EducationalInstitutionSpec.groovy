package com.github.maleksandrowicz93.educational.institution

import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FacultySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot
import spock.lang.Specification

import static com.github.maleksandrowicz93.educational.institution.utils.FacultyUtils.facultySetup

class EducationalInstitutionSpec extends Specification {

    def "faculty may be created"() {
        given: "Educational Institution without any faculty"
        def snapshot = educationalInstitutionSnapshot()
        def educationalInstitution = EducationalInstitution.from(snapshot)

        and: "faculty to be created"
        def facultySetup = facultySetup(snapshot.id())

        when: "the faculty is created"
        def faculty = educationalInstitution.createFaculty(facultySetup)

        then: "should be created successfully"
        def currentSnapshot = educationalInstitution.createSnapshot()
        currentSnapshot.faculties().contains(faculty.id())
        validateFaculty(faculty, facultySetup, currentSnapshot.id())
    }

    private def educationalInstitutionSnapshot() {
        EducationalInstitutionSnapshot.builder()
                .id(new EducationalInstitutionId(UUID.randomUUID()))
                .name("Wroclaw University of Technology")
                .faculties(Set.of())
                .build()
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
