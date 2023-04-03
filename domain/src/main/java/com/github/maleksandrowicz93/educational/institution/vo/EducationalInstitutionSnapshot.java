package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public record EducationalInstitutionSnapshot(
        EducationalInstitutionId id,
        String name,
        Set<FacultyId> faculties
) {
    public EducationalInstitutionSnapshot {
        if (faculties == null) {
            faculties = new HashSet<>();
        }
    }
}
