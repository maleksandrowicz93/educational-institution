package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record PersonalData(
        PersonalIdentification personalIdentification,
        Email email,
        PhoneNumber phoneNumber,
        PersonName personName
) {
}
