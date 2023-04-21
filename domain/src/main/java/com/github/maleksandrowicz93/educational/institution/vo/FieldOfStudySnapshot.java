package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import lombok.Builder;

@Builder
public record FieldOfStudySnapshot(
        FieldOfStudyId id,
        String name,
        FacultyId facultyId
) implements Snapshot<FieldOfStudyId> {
}
