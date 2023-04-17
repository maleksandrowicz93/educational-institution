package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.CourseCloserAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.experimental.SuperBuilder;

@SuperBuilder
class CourseCloserAggregateRoot extends CourseCloserAggregate {

    final CourseId courseId;
    final Threshold minimumEnrollmentsCourseCannotBeClosed;

    Professor professor;
    CourseState state;

    static CourseCloserAggregate from(CourseSnapshot source) {
        return builder()
                .source(source)
                .courseId(source.id())
                .minimumEnrollmentsCourseCannotBeClosed(source.courseManagementThresholds()
                        .minimumEnrollmentsCourseCannotBeClosed())
                .professor(Professor.from(source.professor()))
                .state(source.state())
                .build();
    }

    @Override
    public CourseSnapshot createSnapshot() {
        return source().toBuilder()
                .professor(professor.createSnapshot())
                .state(state)
                .build();
    }

    @Override
    public Result<CourseSnapshot> considerClosingCourse() {
        return null;
    }
}
