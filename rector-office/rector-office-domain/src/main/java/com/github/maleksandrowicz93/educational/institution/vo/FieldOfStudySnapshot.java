package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import lombok.Builder;

@Builder(toBuilder = true)
public record FieldOfStudySnapshot(
        FieldOfStudyId id,
        String name
) implements Snapshot<FieldOfStudyId> {
}
