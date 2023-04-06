package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionId
import com.github.maleksandrowicz93.educational.institution.vo.EducationalInstitutionSnapshot

class EducationalInstitutionUtils {

    private EducationalInstitutionUtils() {}

    static def newEducationalInstitution() {
        EducationalInstitutionSnapshot.builder()
                .id(new EducationalInstitutionId(UUID.randomUUID()))
                .name("Wroclaw University of Technology")
                .faculties(Set.of())
                .build()
    }
}
