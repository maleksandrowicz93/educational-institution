package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.FacultySetup
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyName

import static java.util.stream.Collectors.toSet

class FacultyUtils {

    static final def FACULTY_NAME = "Mechanical"
    static final def MAIN_FIELD_OF_STUDY = "Mechanics and Construction of Machines"
    static final def SECONDARY_FIELDS_OF_STUDY = Set.of("Mechatronics", "Automation and Robotics")

    private FacultyUtils() {}

    static def facultySetup() {
        FacultySetup.builder()
                .name(FACULTY_NAME)
                .mainFieldOfStudyName(new FieldOfStudyName(MAIN_FIELD_OF_STUDY))
                .secondaryFieldsOfStudyNames(SECONDARY_FIELDS_OF_STUDY.stream()
                        .map { new FieldOfStudyName(it) }
                        .collect(toSet()))
                .build()
    }
}
