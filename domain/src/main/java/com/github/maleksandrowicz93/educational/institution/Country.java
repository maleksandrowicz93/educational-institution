package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record Country(
        Name name,
        Alpha2 alpha2,
        Alpha3 alpha3,
        CountryCode countryCode
) {
}
