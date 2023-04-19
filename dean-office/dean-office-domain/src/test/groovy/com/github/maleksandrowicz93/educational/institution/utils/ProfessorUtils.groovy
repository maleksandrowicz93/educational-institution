package com.github.maleksandrowicz93.educational.institution.utils


import com.github.maleksandrowicz93.educational.institution.vo.Professor
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId

import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.FIELDS_OF_STUDY

class ProfessorUtils {

    private ProfessorUtils() {}

    static def newProfessor() {
        Professor.builder()
                .id(new ProfessorId(UUID.randomUUID()))
                .fieldsOfStudy(FIELDS_OF_STUDY)
                .build()
    }
}
