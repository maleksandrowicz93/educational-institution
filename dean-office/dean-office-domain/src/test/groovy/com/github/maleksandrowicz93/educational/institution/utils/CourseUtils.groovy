package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.CourseProposition
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId
import com.github.maleksandrowicz93.educational.institution.vo.Professor
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId

import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.BASIC_THRESHOLD

class CourseUtils {

    private CourseUtils() {}

    static def courseProposition(ProfessorId professorId) {
        configurableCourseProposition(professorId, FIELDS_OF_STUDY)
    }

    private static def configurableCourseProposition(ProfessorId professorId, Set<FieldOfStudyId> fieldsOfStudy) {
        CourseProposition.builder()
                .name("Basics of Programming")
                .leadingProfessor(professorId)
                .fieldsOfStudy(fieldsOfStudy)
                .maximumNumberOfStudents(BASIC_THRESHOLD)
                .build()
    }

    static def courseProposition(ProfessorId professorId, Set<FieldOfStudyId> fieldsOfStudy) {
        configurableCourseProposition(professorId, fieldsOfStudy)
    }

    static def newCourse(Professor leadingProfessor) {
        CourseSnapshot.builder()
                .id(new CourseId(UUID.randomUUID()))
                .leadingProfessor(leadingProfessor.id())
                .fieldsOfStudy(leadingProfessor.fieldsOfStudy())
                .build()
    }
}
