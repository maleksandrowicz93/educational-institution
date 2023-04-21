package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record DeanOfficeSnapshot(
        FacultyId id,
        CourseCreationThresholds thresholds,
        @Singular(ignoreNullCollections = true) Set<Professor> professors,
        @Singular(ignoreNullCollections = true) Set<CourseSnapshot> courses
) implements Snapshot<FacultyId> {
}
