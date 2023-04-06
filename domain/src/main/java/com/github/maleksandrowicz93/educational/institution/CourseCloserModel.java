package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.results.CourseClosingResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.Builder;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseCloserModel implements CourseCloser {

    final CourseSnapshot source;
    final CourseId courseId;
    final Threshold minimumEnrollmentsCourseCannotBeClosed;

    Professor professor;
    CourseState state;

    static CourseCloser from(CourseSnapshot source) {
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
        return source.toBuilder()
                .professor(professor.createSnapshot())
                .state(state)
                .build();
    }

    @Override
    public CourseClosingResult considerClosingCourse() {
        return null;
    }
}
