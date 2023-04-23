package com.github.maleksandrowicz93.educational.institution.utils


import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingThresholds
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

class ThresholdsUtils {

    static final def BASIC_THRESHOLD = new Threshold(2)

    private ThresholdsUtils() {}

    static def courseOvertakingThresholds() {
        CourseOvertakingThresholds.builder()
                .maximumProfessorCourses(BASIC_THRESHOLD)
                .build()
    }
}
