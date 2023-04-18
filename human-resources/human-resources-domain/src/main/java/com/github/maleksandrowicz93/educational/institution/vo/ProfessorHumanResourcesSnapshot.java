package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record ProfessorHumanResourcesSnapshot(
        FacultyId id,
        ProfessorHiringThresholds thresholds,
        FieldOfStudyId mainFieldOfStudy,
        @Singular(value = "secondaryFieldOfStudy", ignoreNullCollections = true)
        Set<FieldOfStudyId> secondaryFieldsOfStudy,
        @Singular(ignoreNullCollections = true) Set<ProfessorSnapshot> professors
) implements Snapshot<FacultyId> {
}
