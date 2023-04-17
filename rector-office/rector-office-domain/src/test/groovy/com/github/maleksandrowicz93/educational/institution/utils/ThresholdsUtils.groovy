package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds
import com.github.maleksandrowicz93.educational.institution.vo.FacultyManagementThresholds
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorHiringThresholds
import com.github.maleksandrowicz93.educational.institution.vo.StudentEnrollmentThresholds
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

class ThresholdsUtils {

    static final def BASIC_THRESHOLD = new Threshold(2)

    private ThresholdsUtils() {}

    static def facultyManagementThresholds() {
        FacultyManagementThresholds.builder()
                .professorHiringThresholds(ProfessorHiringThresholds.builder()
                        .minimumYearsOfExperience(BASIC_THRESHOLD)
                        .minimumKnownFieldsOfStudy(BASIC_THRESHOLD)
                        .maximumVacancies(BASIC_THRESHOLD)
                        .build())
                .studentEnrollmentThresholds(StudentEnrollmentThresholds.builder()
                        .minimumMainFieldOfStudyExamScore(BASIC_THRESHOLD)
                        .minimumSecondaryFieldsOfStudyExamsScore(BASIC_THRESHOLD)
                        .maximumVacancies(BASIC_THRESHOLD)
                        .build())
                .courseCreationThresholds(CourseCreationThresholds.builder()
                        .minimumCourseFieldsOfStudy(BASIC_THRESHOLD)
                        .maximumCourseFieldsOfStudy(BASIC_THRESHOLD)
                        .maximumFacultyCourses(BASIC_THRESHOLD)
                        .maximumProfessorCourses(BASIC_THRESHOLD)
                        .build())
                .build()
    }
}
