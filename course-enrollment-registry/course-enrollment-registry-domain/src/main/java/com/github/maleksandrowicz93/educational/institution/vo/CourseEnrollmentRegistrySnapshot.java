package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.CourseState;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record CourseEnrollmentRegistrySnapshot(
        CourseId id,
        CourseEnrollmentThresholds thresholds,
        CourseState courseState,
        @Singular(ignoreNullCollections = true) Set<StudentId> students
) implements Snapshot<CourseId> {
}
