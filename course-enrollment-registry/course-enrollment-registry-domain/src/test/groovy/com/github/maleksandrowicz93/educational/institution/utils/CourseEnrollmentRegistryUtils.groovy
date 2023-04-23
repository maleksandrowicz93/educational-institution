package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentRegistrySnapshot
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.StudentId

import static com.github.maleksandrowicz93.educational.institution.enums.CourseState.CLOSED
import static com.github.maleksandrowicz93.educational.institution.enums.CourseState.OPEN
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.courseEnrollmentThresholds

class CourseEnrollmentRegistryUtils {

    private CourseEnrollmentRegistryUtils() {}

    static def newCourseRegistry() {
        courseRegistryConfiguredWith(OPEN, Set.of())
    }

    private static def courseRegistryConfiguredWith(CourseState courseState, Set<StudentId> students) {
        CourseEnrollmentRegistrySnapshot.builder()
                .id(new CourseId(UUID.randomUUID()))
                .thresholds(courseEnrollmentThresholds())
                .courseState(courseState)
                .students(students)
                .build()
    }

    static def fullCourseRegistry() {
        def students = Set.of(new StudentId(UUID.randomUUID()), new StudentId(UUID.randomUUID()))
        courseRegistryConfiguredWith(OPEN, students)
    }

    static def closedCourse() {
        courseRegistryConfiguredWith(CLOSED, Set.of())
    }
}
