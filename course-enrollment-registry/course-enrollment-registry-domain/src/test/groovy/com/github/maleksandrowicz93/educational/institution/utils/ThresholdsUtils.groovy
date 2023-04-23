package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentThresholds
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

class ThresholdsUtils {

    static final def BASIC_THRESHOLD = new Threshold(2)

    private ThresholdsUtils() {}

    static def courseEnrollmentThresholds() {
        CourseEnrollmentThresholds.builder()
                .maximumNumberOfEnrolledStudents(BASIC_THRESHOLD)
                .minimumEnrollmentsCourseCannotBeClosed(BASIC_THRESHOLD)
                .build()
    }
}
