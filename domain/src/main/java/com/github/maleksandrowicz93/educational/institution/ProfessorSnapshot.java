package com.github.maleksandrowicz93.educational.institution;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Set;

@Builder(access = AccessLevel.PROTECTED)
record ProfessorSnapshot(
        ProfessorId id,
        PersonalData personalData,
        Set<FieldOfStudyId> fieldsOfStudy,
        FacultyId facultyId,
        EmploymentState employmentState
) {
}
