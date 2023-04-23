package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingApplication
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId

import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.FIELDS_OF_STUDY

class CandidatesUtils {

    private CandidatesUtils() {}

    static def courseOvertakingApplication(ProfessorId professorId) {
        CourseOvertakingApplication.builder()
                .professorId(professorId)
                .fieldsOfStudy(FIELDS_OF_STUDY)
                .build()
    }

    private static def courseOvertakingApplicationConfiguredWith(
            ProfessorId professorId,
            Set<FieldOfStudyId> fieldsOfStudy,
            Set<CourseId> ledCourses
    ) {
        CourseOvertakingApplication.builder()
                .professorId(professorId)
                .fieldsOfStudy(fieldsOfStudy)
                .ledCourses(ledCourses)
                .build()
    }

    static def courseOvertakingApplication(ProfessorId professorId, Set<FieldOfStudyId> fieldsOfStudy) {
        CourseOvertakingApplication.builder()
                .professorId(professorId)
                .fieldsOfStudy(fieldsOfStudy)
                .build()
    }

    static def courseOvertakingApplicationWithNoCapacity(ProfessorId professorId) {
        CourseOvertakingApplication.builder()
                .professorId(professorId)
                .fieldsOfStudy(FIELDS_OF_STUDY)
                .ledCourses(Set.of(new CourseId(UUID.randomUUID()), new CourseId(UUID.randomUUID())))
                .build()
    }
}
