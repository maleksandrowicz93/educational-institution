package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

class ThresholdsUtils {

    static final def BASIC_THRESHOLD = new Threshold(2)

    private ThresholdsUtils() {}

    static def courseCreationThresholds() {
        courseCreationThresholds(BASIC_THRESHOLD)
    }

    static def courseCreationThresholds(Threshold maximumProfessorCourses) {
        CourseCreationThresholds.builder()
                .minimumCourseFieldsOfStudy(BASIC_THRESHOLD)
                .maximumCourseFieldsOfStudy(BASIC_THRESHOLD)
                .maximumFacultyCourses(BASIC_THRESHOLD)
                .maximumProfessorCourses(maximumProfessorCourses)
                .build()
    }
}
