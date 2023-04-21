package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.LeadershipState;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record CourseLeadershipSnapshot(
        CourseId id,
        CourseOvertakingThresholds thresholds,
        @Singular(value = "fieldOfStudy", ignoreNullCollections = true) Set<FieldOfStudyId> fieldsOfStudy,
        ProfessorId leadingProfessor,
        LeadershipState leadershipState
) implements Snapshot<CourseId> {
}
