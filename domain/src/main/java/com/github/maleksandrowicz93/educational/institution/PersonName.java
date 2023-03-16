package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record PersonName(
        Name firstName,
        Name secondName,
        Name lastName
) {
}
