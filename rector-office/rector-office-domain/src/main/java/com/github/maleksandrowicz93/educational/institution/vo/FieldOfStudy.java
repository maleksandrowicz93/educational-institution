package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record FieldOfStudy(
        @NonNull FieldOfStudyId id,
        @NonNull String name
) {
}
