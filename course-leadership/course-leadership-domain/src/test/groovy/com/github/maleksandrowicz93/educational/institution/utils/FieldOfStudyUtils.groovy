package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId

class FieldOfStudyUtils {

    private static final def FIELD_OF_STUDY_1 = generateFieldOfStudyId()
    private static final def FIELD_OF_STUDY_2 = generateFieldOfStudyId()
    static final def FIELDS_OF_STUDY = Set.of(FIELD_OF_STUDY_1, FIELD_OF_STUDY_2)

    private FieldOfStudyUtils() {}

    static def tooFewFieldsOfStudy() {
        Set.of(FIELD_OF_STUDY_1)
    }

    private static def generateFieldOfStudyId() {
        new FieldOfStudyId(UUID.randomUUID())
    }

    static def notMatchedFieldsOfStudy() {
        Set.of(generateFieldOfStudyId(), generateFieldOfStudyId())
    }
}
