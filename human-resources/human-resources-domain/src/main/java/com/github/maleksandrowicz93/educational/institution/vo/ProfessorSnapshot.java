package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.common.Snapshot;
import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState;
import lombok.Builder;

@Builder(toBuilder = true)
public record ProfessorSnapshot(
        ProfessorId id,
        PersonalData personalData,
        EmploymentState employmentState
) implements Snapshot<ProfessorId> {
}
