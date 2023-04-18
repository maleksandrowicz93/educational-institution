package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

@Builder
public record Professor(
        @NonNull ProfessorId id,
        @Singular(value = "fieldOfStudy", ignoreNullCollections = true) Set<FieldOfStudyId> fieldsOfStudy
) {
}
