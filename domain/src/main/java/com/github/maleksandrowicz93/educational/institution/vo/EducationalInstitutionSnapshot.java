package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.api.Snapshot;
import lombok.Builder;
import lombok.Singular;

import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
public record EducationalInstitutionSnapshot(
        EducationalInstitutionId id,
        String name,
        @Singular Set<FacultyId> faculties
) implements Snapshot<EducationalInstitutionId> {
    public EducationalInstitutionSnapshot {
        if (faculties == null) {
            faculties = new HashSet<>();
        }
    }
}
