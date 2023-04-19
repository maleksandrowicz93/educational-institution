package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.CourseCreationThresholds
import com.github.maleksandrowicz93.educational.institution.vo.DeanOfficeSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.courseCreationThresholds

class DeanOfficeUtils {

    private DeanOfficeUtils() {}

    static def newDeanOffice() {
        configurableDeanOffice(courseCreationThresholds())
    }

    private static def configurableDeanOffice(CourseCreationThresholds thresholds) {
        DeanOfficeSnapshot.builder()
                .id(new FacultyId(UUID.randomUUID()))
                .thresholds(thresholds)
                .build()
    }

    static def newDeanOffice(Threshold maximumProfessorCourses) {
        configurableDeanOffice(courseCreationThresholds(maximumProfessorCourses))
    }
}
