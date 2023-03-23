package com.github.maleksandrowicz93.educational.institution.vo;

import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

@Builder
public record ProfessorApplication(
        @NonNull PersonalData personalData,
        @NonNull YearsOfExperience yearsOfExperience,
        @NonNull Set<FieldOfStudyId> fieldsOfStudy,
        @NonNull FacultyId facultyId
) {
}
