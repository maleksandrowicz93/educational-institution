package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder
public record StudentHumanResourcesSnapshot(
        FacultyId id,
        StudentEnrollmentThresholds thresholds,
        @Singular(ignoreNullCollections = true) Set<StudentSnapshot> students
) implements Snapshot<FacultyId> {
}
