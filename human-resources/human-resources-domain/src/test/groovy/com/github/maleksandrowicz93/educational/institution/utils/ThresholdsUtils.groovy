package com.github.maleksandrowicz93.educational.institution.utils


import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds
import com.github.maleksandrowicz93.educational.institution.vo.StudentEnrollmentThresholds
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

class ThresholdsUtils {

    static final def BASIC_THRESHOLD = new Threshold(2)

    private ThresholdsUtils() {}

    static def professorHiringThresholds() {
        ProfessorHiringThresholds.builder()
                .minimumYearsOfExperience(BASIC_THRESHOLD)
                .minimumKnownFieldsOfStudy(BASIC_THRESHOLD)
                .maximumVacancies(BASIC_THRESHOLD)
                .build()
    }

    static def studentEnrollmentThresholds() {
        StudentEnrollmentThresholds.builder()
                .minimumMainFieldOfStudyExamScore(BASIC_THRESHOLD)
                .minimumSecondaryFieldsOfStudyExamsScore(BASIC_THRESHOLD)
                .maximumVacancies(BASIC_THRESHOLD)
                .build()
    }
}
