package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.aggregates.CourseLeadershipAggregate;
import com.github.maleksandrowicz93.educational.institution.common.Result;
import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@SuperBuilder
class CourseLeadershipAggregateRoot implements CourseLeadershipAggregate {

    @Getter(PRIVATE)
    final CourseSnapshot source;
    @Getter
    final CourseId id;
    final Threshold maximumProfessorCourses;
    final Set<FieldOfStudy> fieldsOfStudy;

    Professor professor;
    CourseState state;

    static CourseLeadershipAggregate from(CourseSnapshot source) {
        return builder()
                .source(source)
                .id(source.id())
                .maximumProfessorCourses(source.courseManagementThresholds().maximumProfessorCourses())
                .fieldsOfStudy(source.fieldsOfStudy().stream()
                        .map(FieldOfStudy::from)
                        .collect(toSet()))
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
    public CourseSnapshot receiveCourseLeadingResignation() {
        return null;
    }

    @Override
    public Result<CourseSnapshot> considerCourseOvertaking(ProfessorSnapshot professor) {
        return null;
    }
}
