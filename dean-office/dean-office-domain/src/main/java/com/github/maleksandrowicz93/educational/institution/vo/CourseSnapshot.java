package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@Builder
public record CourseSnapshot(
        @NonNull CourseId id,
        @NonNull String name,
        @NonNull ProfessorId professorId,
        @Singular(value = "fieldOfStudy", ignoreNullCollections = true) Set<FieldOfStudyId> fieldsOfStudy
) implements Snapshot<CourseId> {
    public CourseSnapshot {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("CourseSnapshot name cannot be blank.");
        }
    }
}
