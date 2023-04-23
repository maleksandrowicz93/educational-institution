package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId

class FieldOfStudyUtils {

    static final def MAIN_FIELD_OF_STUDY = generateFieldOfStudyId()
    static final def SECONDARY_FIELDS_OF_STUDY = Set.of(generateFieldOfStudyId(), generateFieldOfStudyId())
    static final def NOT_KNOWN_FIELDS_OF_STUDY = Set.of(generateFieldOfStudyId(), generateFieldOfStudyId())

    private FieldOfStudyUtils() {}

    private static def generateFieldOfStudyId() {
        new FieldOfStudyId(UUID.randomUUID())
    }
}
