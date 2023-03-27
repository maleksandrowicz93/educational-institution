package com.github.maleksandrowicz93.educational.institution.vo;

import com.github.maleksandrowicz93.educational.institution.enums.EmploymentState;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public record ProfessorSnapshot(
        ProfessorId id,
        PersonalData personalData,
        Set<FieldOfStudyId> fieldsOfStudy,
        FacultyId facultyId,
        EmploymentState employmentState
) {
    public ProfessorSnapshot {
        if (fieldsOfStudy == null) {
            fieldsOfStudy = new HashSet<>();
        }
    }
}
