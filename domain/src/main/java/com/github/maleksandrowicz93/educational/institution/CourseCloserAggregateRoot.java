package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.CourseCloserAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseCloserAggregateRoot implements CourseCloserAggregate {

    @Getter(PRIVATE)
    final CourseSnapshot source;
    @Getter
    final CourseId id;
    final Threshold minimumEnrollmentsCourseCannotBeClosed;

    Professor professor;
    CourseState state;

    static CourseCloserAggregate from(CourseSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
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
