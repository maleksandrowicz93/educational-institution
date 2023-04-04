package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudySnapshot

import static java.util.stream.Collectors.toSet

class FieldOfStudyUtils {

    static final def MAIN_FIELD_OF_STUDY = "Mechanics and Construction of Machines"
    static final def SECONDARY_FIELDS_OF_STUDY = Set.of("Mechatronics", "Automation and Robotics")

    private FieldOfStudyUtils() {}

    static def fieldsOfStudyFromNames(Set<String> fieldsOfStudyNames, FacultyId facultyId) {
        fieldsOfStudyNames.stream()
                .map { fieldOfStudy(it, facultyId) }
                .collect(toSet())
    }

    static def fieldOfStudy(String fieldOfStudyName, FacultyId facultyId) {
        FieldOfStudySnapshot.builder()
                .id(new FieldOfStudyId(UUID.randomUUID()))
                .name(fieldOfStudyName)
                .facultyId(facultyId)
                .build()
    }
}
