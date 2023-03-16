package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record FieldOfStudySnapshot(
        FieldOfStudyId id,
        Name name
) {
}
