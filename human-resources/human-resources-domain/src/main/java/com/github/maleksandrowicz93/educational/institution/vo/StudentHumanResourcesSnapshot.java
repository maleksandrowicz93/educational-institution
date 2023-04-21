package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record StudentHumanResourcesSnapshot(
        FacultyId id,
        StudentEnrollmentThresholds thresholds,
        FieldOfStudyId mainFieldOfStudy,
        @Singular(value = "secondaryFieldOfStudy", ignoreNullCollections = true)
        Set<FieldOfStudyId> secondaryFieldsOfStudy,
        @Singular(ignoreNullCollections = true) Set<StudentSnapshot> students
) implements Snapshot<FacultyId> {
}
