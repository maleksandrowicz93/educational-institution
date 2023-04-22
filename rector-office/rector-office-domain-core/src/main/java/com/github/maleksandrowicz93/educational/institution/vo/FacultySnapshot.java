package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record FacultySnapshot(
        FacultyId id,
        String name,
        FieldOfStudySnapshot mainFieldOfStudy,
        @Singular(value = "secondaryFieldOfStudy", ignoreNullCollections = true)
        Set<FieldOfStudySnapshot> secondaryFieldsOfStudy
) implements Snapshot<FacultyId> {
}
