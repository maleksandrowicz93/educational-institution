package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeId
import com.github.maleksandrowicz93.educational.institution.vo.RectorOfficeSnapshot

import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.facultyManagementThresholds

class RectorOfficeUtils {

    private RectorOfficeUtils() {}

    static def newRectorOffice() {
        RectorOfficeSnapshot.builder()
                .id(new RectorOfficeId(UUID.randomUUID()))
                .name("Wroclaw University of Technology")
                .thresholds(facultyManagementThresholds())
                .faculties(Set.of())
                .build()
    }
}
