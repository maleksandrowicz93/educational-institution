package com.github.maleksandrowicz93.educational.institution.utils

import com.github.maleksandrowicz93.educational.institution.enums.CourseState
import com.github.maleksandrowicz93.educational.institution.vo.CourseId
import com.github.maleksandrowicz93.educational.institution.vo.CourseManagementThresholds
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot
import com.github.maleksandrowicz93.educational.institution.vo.FacultyId
import com.github.maleksandrowicz93.educational.institution.vo.Threshold

import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.SECONDARY_FIELDS_OF_STUDY
import static com.github.maleksandrowicz93.educational.institution.utils.FieldOfStudyUtils.fieldsOfStudyFromNames
import static com.github.maleksandrowicz93.educational.institution.utils.ThresholdsUtils.courseManagementThresholds

class CourseUtils {

    private CourseUtils() {}

    static def newCourse() {
        newCourseConfiguredWith(courseManagementThresholds())
    }

    static def newCourse(Threshold maximumProfessorCourses) {
        newCourseConfiguredWith(courseManagementThresholds(maximumProfessorCourses))
    }

    private static def newCourseConfiguredWith(CourseManagementThresholds thresholds) {
        def facultyId = new FacultyId(UUID.randomUUID())
        CourseSnapshot.builder()
                .id(new CourseId(UUID.randomUUID()))
                .facultyId(facultyId)
                .name("Basics of Programming")
                .courseManagementThresholds(thresholds)
                .fieldsOfStudy(fieldsOfStudyFromNames(SECONDARY_FIELDS_OF_STUDY, facultyId))
                .students(Set.of())
                .state(CourseState.LED)
                .build()
    }
}
