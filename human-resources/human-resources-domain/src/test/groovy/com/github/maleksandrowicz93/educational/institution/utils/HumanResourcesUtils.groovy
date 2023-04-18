package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHumanResourcesSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.StudentHumanResourcesSnapshot

import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.MAIN_FIELD_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.SECONDARY_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.professorHiringThresholds
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.studentEnrollmentThresholds

class HumanResourcesUtils {

    private HumanResourcesUtils() {}

    static def newProfessorHumanResources() {
        ProfessorHumanResourcesSnapshot.builder()
                .id(new FacultyId(UUID.randomUUID()))
                .thresholds(professorHiringThresholds())
                .mainFieldOfStudy(MAIN_FIELD_OF_STUDY)
                .secondaryFieldsOfStudy(SECONDARY_FIELDS_OF_STUDY)
                .professors(Set.of())
                .build()
    }

    static def newStudentHumanResources() {
        StudentHumanResourcesSnapshot.builder()
                .id(new FacultyId(UUID.randomUUID()))
                .thresholds(studentEnrollmentThresholds())
                .mainFieldOfStudy(MAIN_FIELD_OF_STUDY)
                .secondaryFieldsOfStudy(SECONDARY_FIELDS_OF_STUDY)
                .students(Set.of())
                .build()
    }
}
