package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import com.github.maleksandrowicz93.educational.institution.results.CourseOvertakingResult;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.Threshold;
import lombok.Builder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseLeadershipModel implements CourseLeadership {

    final CourseSnapshot source;
    final CourseId courseId;
    final Threshold maximumProfessorCourses;
    final Set<FieldOfStudy> fieldsOfStudy;

    Professor professor;
    CourseState state;

    static CourseLeadership from(CourseSnapshot source) {
        return builder()
                .source(source)
                .courseId(source.id())
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
        return source.toBuilder()
                .professor(professor.createSnapshot())
                .state(state)
                .build();
    }

    @Override
    public CourseSnapshot receiveCourseLeadingResignation() {
        return null;
    }

    @Override
    public CourseOvertakingResult considerCourseOvertaking(ProfessorSnapshot professor) {
        return null;
    }
}
