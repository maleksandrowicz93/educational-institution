package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record PersonalIdentification(
        NationalIdentificationNumber nationalIdentificationNumber,
        Country country
) {
}
