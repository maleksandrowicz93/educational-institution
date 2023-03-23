package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

import java.util.Locale;

@Builder
public record PersonalIdentification(
        @NonNull NationalIdentificationNumber nationalIdentificationNumber,
        @NonNull Locale country
) {
}
