package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState;
import lombok.Builder;
import lombok.Singular;

import java.util.Set;

@Builder(toBuilder = true)
public record ProfessorSnapshot(
        ProfessorId id,
        PersonalData personalData,
        @Singular(value = "fieldOfStudy", ignoreNullCollections = true) Set<FieldOfStudyId> fieldsOfStudy,
        EmploymentState employmentState
) implements Snapshot<ProfessorId> {
}
