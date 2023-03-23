package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;

import java.util.Set;

@Builder
public record EducationalInstitutionSnapshot(
        EducationalInstitutionId id,
        EducationalInstitutionSetup setup,
        Set<FacultySnapshot> faculties
) {
}
