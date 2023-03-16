package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
record EducationalInstitutionSetup(
        Name name,
        EducationalInstitutionThresholds thresholds
) {
}
