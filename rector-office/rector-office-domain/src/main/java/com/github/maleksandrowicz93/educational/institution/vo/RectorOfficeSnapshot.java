package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record RectorOfficeSnapshot(
        RectorOfficeId id,
        String name,
        FacultyManagementThresholds facultyManagementThresholds,
        @Singular(ignoreNullCollections = true) Set<Faculty> faculties
) implements Snapshot<RectorOfficeId> {
}