package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder
public record ProfessorHumanResourcesSnapshot(
        FacultyId id,
        ProfessorHiringThresholds thresholds,
        @Singular(ignoreNullCollections = true) Set<ProfessorSnapshot> professors
) implements Snapshot<FacultyId> {
}
