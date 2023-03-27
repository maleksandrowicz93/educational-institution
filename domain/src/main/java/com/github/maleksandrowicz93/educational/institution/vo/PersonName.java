package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record PersonName(
        @NonNull BasicPersonName basicPersonName,
        String secondName
) {
    PersonName(@NonNull BasicPersonName basicPersonName) {
        this(basicPersonName, null);
    }
}
