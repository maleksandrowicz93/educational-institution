package com.github.maleksandrowicz93.educational.institution;

import com.github.maleksandrowicz93.educational.institution.api.CourseLeadershipAggregate;
import com.github.maleksandrowicz93.educational.institution.api.Result;
import com.github.maleksandrowicz93.educational.institution.enums.LeadershipState;
import com.github.maleksandrowicz93.educational.institution.events.CourseBecomeFreeEvent;
import com.github.maleksandrowicz93.educational.institution.events.CourseOvertakenEvent;
import com.github.maleksandrowicz93.educational.institution.vo.CourseId;
import com.github.maleksandrowicz93.educational.institution.vo.CourseLeadershipSnapshot;
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingApplication;
import com.github.maleksandrowicz93.educational.institution.vo.CourseOvertakingThresholds;
import com.github.maleksandrowicz93.educational.institution.vo.FieldOfStudyId;
import com.github.maleksandrowicz93.educational.institution.vo.ProfessorId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
class CourseLeadershipAggregateRoot implements CourseLeadershipAggregate {

    @Getter
    final CourseId id;
    final CourseOvertakingThresholds thresholds;
    final Set<FieldOfStudyId> fieldsOfStudy;

    ProfessorId leadingProfessor;
    LeadershipState leadershipState;

    static CourseLeadershipAggregateRoot from(CourseLeadershipSnapshot source) {
        return builder()
                .id(source.id())
                .thresholds(source.thresholds())
                .fieldsOfStudy(source.fieldsOfStudy())
                .leadingProfessor(source.leadingProfessor())
                .leadershipState(source.leadershipState())
                .build();
    }

    @Override
    public CourseLeadershipSnapshot createSnapshot() {
        return CourseLeadershipSnapshot.builder()
                .id(id)
                .thresholds(thresholds)
                .fieldsOfStudy(fieldsOfStudy)
                .leadingProfessor(leadingProfessor)
                .leadershipState(leadershipState)
                .build();
    }

    @Override
    public Result<CourseBecomeFreeEvent> receiveCourseLeadingResignation() {
        return null;
    }

    @Override
    public Result<CourseOvertakenEvent> considerCourseOvertaking(CourseOvertakingApplication application) {
        return null;
    }
}
