package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Singular;

import java.util.Set;

public record Professor(
        ProfessorId id,
        @Singular(ignoreNullCollections = true) Set<CourseId> courses
) {
}
