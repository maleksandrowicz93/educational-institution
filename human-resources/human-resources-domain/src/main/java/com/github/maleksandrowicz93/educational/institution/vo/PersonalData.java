package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record PersonalData(
        @NonNull PersonalIdentification personalIdentification,
        @NonNull Email email,
        @NonNull PhoneNumber phoneNumber,
        @NonNull PersonName personName
) {
}
