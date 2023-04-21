package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder
public record CourseSnapshot(
        CourseId id,
        String name,
        ProfessorId leadingProfessor,
        @Singular(value = "fieldOfStudy", ignoreNullCollections = true) Set<FieldOfStudyId> fieldsOfStudy
) implements Snapshot<CourseId> {
}
