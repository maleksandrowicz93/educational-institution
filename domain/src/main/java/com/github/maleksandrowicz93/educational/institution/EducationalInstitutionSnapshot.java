package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PACKAGE)
record EducationalInstitutionSnapshot(
        EducationalInstitutionId id,
        EducationalInstitutionSetup setup,
        Set<FacultySnapshot> faculties
) {
}
