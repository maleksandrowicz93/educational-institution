package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import lombok.Builder;

@Builder
public record StudentHumanResourcesSnapshot(
        FacultyId id
) implements Snapshot<FacultyId> {
}
