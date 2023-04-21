package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.enums.LeadershipState
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.CourseLeadershipSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId

import static com.github.maleksandrowicz93.educational.institution.enums.LeadershipState.FREE
import static com.github.maleksandrowicz93.educational.institution.enums.LeadershipState.LED
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.courseOvertakingThresholds

class CourseLeadershipUtils {

    private CourseLeadershipUtils() {}

    static def ledCourse(ProfessorId professorId) {
        courseConfiguredWith(professorId, LED)
    }

    private static def courseConfiguredWith(ProfessorId professorId, LeadershipState leadershipState) {
        CourseLeadershipSnapshot.builder()
                .id(new CourseId(UUID.randomUUID()))
                .thresholds(courseOvertakingThresholds())
                .fieldsOfStudy(FIELDS_OF_STUDY)
                .leadingProfessor(professorId)
                .leadershipState(leadershipState)
                .build()
    }

    static def freeCourse() {
        courseConfiguredWith(null, FREE)
    }
}
