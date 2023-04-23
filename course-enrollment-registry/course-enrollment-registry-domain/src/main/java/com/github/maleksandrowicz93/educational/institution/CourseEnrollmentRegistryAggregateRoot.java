package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.CourseEnrollmentRegistryAggregate;
import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.events.CourseClosedEvent;
import com.github.maleksandrowicz93.educational.institution.events.StudentEnrolledForCourseEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentRegistrySnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.CourseEnrollmentThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.StudentId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseEnrollmentRegistryAggregateRoot implements CourseEnrollmentRegistryAggregate {

    @Getter
    final CourseId id;
    final CourseEnrollmentThresholds thresholds;
    final CourseState courseState;
    final Set<StudentId> students;

    static CourseEnrollmentRegistryAggregateRoot from(CourseEnrollmentRegistrySnapshot source) {
        return builder()
                .id(source.id())
                .thresholds(source.thresholds())
                .courseState(source.courseState())
                .students(source.students())
                .build();
    }

    @Override
    public CourseEnrollmentRegistrySnapshot createSnapshot() {
        return CourseEnrollmentRegistrySnapshot.builder()
                .id(id)
                .thresholds(thresholds)
                .courseState(courseState)
                .students(students)
                .build();
    }

    @Override
    public Result<StudentEnrolledForCourseEvent> considerCourseEnrollment(StudentId studentId) {
        return null;
    }

    @Override
    public Result<CourseClosedEvent> considerClosingCourse() {
        return null;
    }
}
